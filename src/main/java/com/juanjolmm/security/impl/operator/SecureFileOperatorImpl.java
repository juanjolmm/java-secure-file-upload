package com.juanjolmm.security.impl.operator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.input.BoundedInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juanjolmm.security.api.operator.SecureConfigurationLoader;
import com.juanjolmm.security.api.operator.SecureFileOperator;
import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.util.SecureFileUtils;

/**
 * Implementation of SecureFileOperator.
 * 
 * @author jlopez1
 *
 */
public final class SecureFileOperatorImpl implements SecureFileOperator {

	private static Logger LOGGER = LoggerFactory.getLogger(SecureFileOperatorImpl.class);
	private final SecureConfigurationLoader configLoader;

	/**
	 * Constructor that establish a secure configuration loader.
	 * 
	 * @param configLoader
	 *            The secure configuration loader.
	 */
	public SecureFileOperatorImpl(@Nonnull final SecureConfigurationLoader configLoader) {
		this.configLoader = configLoader;
	}

	@Override
	@Nonnull
	public File createNewFileInSecureStorage(@Nonnull final InputStream inputStream,
			@Nonnull final String initialFileName) throws SecureFileUploadException {
		File storeFolder = new File(configLoader.getStorageFolder());
		if (!storeFolder.exists()) {
			storeFolder.mkdirs();
		}
		final File newFile = new File(storeFolder, getOperatorFileName(initialFileName));
		copyLimitedAmountOfBytes(inputStream, newFile);
		return newFile;
	}

	@Nonnull
	private String getOperatorFileName(@Nonnull final String initialFileName) throws SecureFileUploadException {
		final String finalFileName;
		switch (configLoader.getFileNameMode()) {
		case ORIGINAL:
			finalFileName = SecureFileUtils.getOriginalBasedFileName(initialFileName);
			break;
		case SECURE:
			finalFileName = SecureFileUtils.getSecureFileName(initialFileName);
			break;
		default:
			finalFileName = SecureFileUtils.getSecureFileName(initialFileName);
		}
		return finalFileName;
	}

	private void copyLimitedAmountOfBytes(@Nonnull final InputStream inputStream,
			@Nonnull final File file) throws SecureFileUploadException {
		OutputStream outputStream = null;
		BoundedInputStream boundedInput = new BoundedInputStream(inputStream, configLoader.getMaxFileSize());
		try {
			outputStream = new FileOutputStream(file);
			IOUtils.copy(boundedInput, outputStream);
			checkStreamSizeLimit(inputStream);
		} catch (IOException | SecureFileUploadException e) {
			IOUtils.closeQuietly(outputStream);
			FileUtils.deleteQuietly(file);
			LOGGER.error(e.getMessage());
			throw new SecureFileUploadException();
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(boundedInput);
			IOUtils.closeQuietly(inputStream);
		}
	}

	private void checkStreamSizeLimit(@Nonnull final InputStream inputStream)
			throws IOException, SecureFileUploadException {
		byte[] buffer = new byte[10];
		if (IOUtils.read(inputStream, buffer) > 0) {
			throw new SecureFileUploadException();
		}
	}

	@Override
	public void setSecureFileOwner(@Nonnull final File file) throws SecureFileUploadException {
		try {
			final Path path = file.toPath();
			final String owner = configLoader.getSecureFileOwner();
			if (owner != null && owner.length() > 0) {
				final UserPrincipalLookupService lookupService = FileSystems.getDefault()
						.getUserPrincipalLookupService();
				final UserPrincipal userPrincipal = lookupService.lookupPrincipalByName(owner);
				Files.setOwner(path, userPrincipal);
			} else {
				LOGGER.warn("Secure owner not established. File owner not changed.");
			}
		} catch (IOException e) {
			LOGGER.error("Error while setting a secure owner in the file." + e.getMessage());
			throw new SecureFileUploadException();
		}
	}

	@Override
	public void setSecureFilePermissions(@Nonnull final File file) throws SecureFileUploadException {
		if (file.exists()) {
			chageFilePermissions(file);
		} else {
			LOGGER.error("Error: Can not chage permissions because the file does not exist.");
			throw new SecureFileUploadException();
		}
	}

	private void chageFilePermissions(@Nonnull final File file) throws SecureFileUploadException {
		try {
			final PosixFileAttributeView posixAttributeView = Files.getFileAttributeView(file.toPath(),
					PosixFileAttributeView.class);
			if (posixAttributeView != null) {
				final Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
				perms.add(PosixFilePermission.OWNER_READ);
				perms.add(PosixFilePermission.OWNER_WRITE);
				posixAttributeView.setPermissions(perms);
			} else {
				LOGGER.warn("File system attribute view not supported.");
			}
		} catch (IOException e) {
			LOGGER.error("Error while changing file permissions." + e.getMessage());
			throw new SecureFileUploadException();
		}
	}

	@Override
	@Nonnull
	public List<File> listUploadedFiles() {
		final List<File> uplodadedFiles = new ArrayList<File>();
		final File storeFolder = new File(configLoader.getStorageFolder());
		if (storeFolder.exists()) {
			uplodadedFiles.addAll(FileUtils.listFiles(storeFolder, TrueFileFilter.INSTANCE, null));
		}
		return uplodadedFiles;
	}

	@Override
	@Nonnull
	public File getUplodadedFile(@Nonnull String fileName) throws FileNotFoundException {
		final File uploadedFile;
		final File storeFolder = new File(configLoader.getStorageFolder());
		if (!storeFolder.exists() || !(uploadedFile = FileUtils.getFile(storeFolder, fileName)).exists()) {
			throw new FileNotFoundException("The file does not exist.");
		}
		return uploadedFile;
	}
}
