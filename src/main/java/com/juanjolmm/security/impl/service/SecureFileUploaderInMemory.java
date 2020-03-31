package com.juanjolmm.security.impl.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.annotation.Nonnull;

import com.juanjolmm.security.api.qualifiers.InMemory;
import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.util.SecureFileUtils;

/**
 * SecureFileUploader implementation that make all the checks in memory. This
 * implementation creates the file after check content-type, extension and
 * header. This implementation is more secure and faster but consumes more
 * memory.
 * 
 * @author jlopez1
 *
 */
@InMemory
public final class SecureFileUploaderInMemory extends AbstractSecureFileUploader {

	/**
	 * Default constructor.
	 */
	public SecureFileUploaderInMemory() {
		super();
	}

	@Override
	@Nonnull
	public File upload(@Nonnull final InputStream inputStream, @Nonnull final String fileName,
			@Nonnull final String requestContentType) throws SecureFileUploadException {
		if (inputStream != null && fileName != null && requestContentType != null) {
			checkContentType(requestContentType);
			final String fileExtension = SecureFileUtils.getFileExtension(fileName);
			checkFileExtension(requestContentType, fileExtension);
			byte[] bytes = SecureFileUtils.getBytesFromInputStream(inputStream);
			checkStreamHeader(new ByteArrayInputStream(bytes), requestContentType, fileExtension);
			final File newFile = fileOperator.createNewFileInSecureStorage(new ByteArrayInputStream(bytes), fileName);
			setSecureOwner(newFile);
			setSecurePermissionsInFile(newFile);
			return newFile;
		} else {
			throw new SecureFileUploadException();
		}
	}

	private void checkStreamHeader(@Nonnull final InputStream inputStream, @Nonnull final String requestContentType,
			@Nonnull final String fileExtension)
			throws SecureFileUploadException {
		if (!requestOperator.isValidStreamHeader(inputStream, requestContentType, fileExtension)) {
			throw new SecureFileUploadException();
		}
	}
}
