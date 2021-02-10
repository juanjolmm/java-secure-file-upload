package com.juanjolmm.security.impl.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import com.juanjolmm.security.api.operator.SecureConfigurationLoader;
import org.apache.commons.io.FileUtils;

import com.juanjolmm.security.api.qualifiers.Stored;
import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.util.SecureFileUtils;

/**
 * SecureFileUploader implementation that make the header check in the file.
 * This implementation creates the file after check content-type and extension.
 * This implementation is less secure but consumes less memory.
 * 
 * @author jlopez1
 *
 */
@Stored
public final class SecureFileUploaderStored extends AbstractSecureFileUploader {

	/**
	 * Default constructor.
	 */
	public SecureFileUploaderStored() {
		super();
	}

	/**
	 * Constructor with custom configLoader
	 *
	 * @param configLoader an implementation of SecureConfigurationLoader
	 */
	public SecureFileUploaderStored(SecureConfigurationLoader configLoader) {
		super(configLoader);
	}

	@Override
	@Nonnull
	public File upload(@Nonnull final InputStream inputStream, @Nonnull final String fileName,
			@Nonnull final String requestContentType) throws SecureFileUploadException {
		if (inputStream != null && fileName != null && requestContentType != null) {
			checkContentType(requestContentType);
			final String fileExtension = SecureFileUtils.getFileExtension(fileName);
			checkFileExtension(requestContentType, fileExtension);
			final File newFile = fileOperator.createNewFileInSecureStorage(inputStream, fileName);
			setSecureOwner(newFile);
			setSecurePermissionsInFile(newFile);
			checkStreamHeader(newFile, requestContentType, fileExtension);
			return newFile;
		} else {
			throw new SecureFileUploadException();
		}
	}

	private void checkStreamHeader(@Nonnull final File newFile, @Nonnull final String requestContentType,
			@Nonnull final String fileExtension)
			throws SecureFileUploadException {
		try {
			if (!requestOperator.isValidStreamHeader(new FileInputStream(newFile), requestContentType, fileExtension)) {
				FileUtils.deleteQuietly(newFile);
				throw new SecureFileUploadException();
			}
		} catch (IOException e) {
			FileUtils.deleteQuietly(newFile);
			throw new SecureFileUploadException();
		}
	}
}
