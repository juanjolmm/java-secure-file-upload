package com.juanjolmm.security.impl.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;

import com.juanjolmm.security.api.operator.SecureConfigurationLoader;
import com.juanjolmm.security.api.operator.SecureFileOperator;
import com.juanjolmm.security.api.operator.SecureRequestOperator;
import com.juanjolmm.security.api.service.SecureFileUploader;
import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.operator.SecureConfigurationLoaderImpl;
import com.juanjolmm.security.impl.operator.SecureFileOperatorImpl;
import com.juanjolmm.security.impl.operator.SecureRequestOperatorImpl;
import com.juanjolmm.security.impl.util.SecureFileUtils;

/**
 * Abstract class to define the common methods and variables to use in other
 * implementations.
 * 
 * @author jlopez1
 *
 */
public abstract class AbstractSecureFileUploader implements SecureFileUploader {

	protected final SecureRequestOperator requestOperator;
	protected final SecureFileOperator fileOperator;

	/**
	 * Default constructor.
	 */
	public AbstractSecureFileUploader() {
		SecureConfigurationLoader configLoader = new SecureConfigurationLoaderImpl();
		requestOperator = new SecureRequestOperatorImpl(configLoader);
		fileOperator = new SecureFileOperatorImpl(configLoader);
	}

	@Override
	@Nonnull
	public abstract File upload(@Nonnull final InputStream inputStream, @Nonnull final String fileName,
			@Nonnull final String requestContentType) throws SecureFileUploadException;

	protected void checkContentType(@Nonnull final String requestContentType) throws SecureFileUploadException {
		if (!requestOperator.isValidRequestContentType(requestContentType)) {
			throw new SecureFileUploadException();
		}
	}

	protected void checkFileExtension(@Nonnull final String requestContentType, @Nonnull final String fileExtension)
			throws SecureFileUploadException {
		if (!requestOperator.isValidFileExtension(requestContentType, fileExtension)) {
			throw new SecureFileUploadException();
		}
	}

	protected void setSecureOwner(@Nonnull final File newFile) throws SecureFileUploadException {
		try {
			fileOperator.setSecureFileOwner(newFile);
		} catch (SecureFileUploadException e) {
			FileUtils.deleteQuietly(newFile);
			throw new SecureFileUploadException();
		}
	}

	protected void setSecurePermissionsInFile(@Nonnull final File newFile) throws SecureFileUploadException {
		try {
			fileOperator.setSecureFilePermissions(newFile);
		} catch (SecureFileUploadException e) {
			FileUtils.deleteQuietly(newFile);
			throw new SecureFileUploadException();
		}
	}

	@Override
	@Nonnull
	public List<File> list() {
		return fileOperator.listUploadedFiles();
	}

	@Override
	@Nonnull
	public File get(@Nonnull String fileName) throws FileNotFoundException, SecureFileUploadException {
		final File uplodadedFile = fileOperator.getUplodadedFile(fileName);
		final String contentType = SecureFileUtils.detectFileContentType(new FileInputStream(uplodadedFile));
		checkContentType(contentType);
		checkFileExtension(contentType, SecureFileUtils.getFileExtension(fileName));
		return uplodadedFile;
	}
}
