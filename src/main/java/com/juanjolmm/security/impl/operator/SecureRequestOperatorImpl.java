package com.juanjolmm.security.impl.operator;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Nonnull;

import com.juanjolmm.security.api.operator.SecureConfigurationLoader;
import com.juanjolmm.security.api.operator.SecureRequestOperator;
import com.juanjolmm.security.impl.util.SecureFileUtils;

/**
 * SecureUploadOperator implementation.
 * 
 * @author jlopez1
 *
 */
public final class SecureRequestOperatorImpl implements SecureRequestOperator {

	private static final String DEFAULT_CONTENT_TYPE_NOT_ALLOWED = "application/octet-stream";
	private final SecureConfigurationLoader configLoader;

	/**
	 * Constructor that establish a secure configuration loader.
	 * 
	 * @param configLoader
	 *            The secure configuration loader.
	 */
	public SecureRequestOperatorImpl(@Nonnull final SecureConfigurationLoader configLoader) {
		this.configLoader = configLoader;
	}

	@Override
	public boolean isValidRequestContentType(@Nonnull final String requestContentType) {
		return requestContentType != null && configLoader.existsContentType(requestContentType);
	}

	@Override
	public boolean isValidFileExtension(@Nonnull final String contentType, @Nonnull final String fileExtension) {
		return fileExtension != null && isExtensionAllowedInContentType(contentType, fileExtension);
	}

	private boolean isExtensionAllowedInContentType(@Nonnull final String contentType,
			@Nonnull final String fileNameExt) {
		boolean isAllowed = false;
		final List<String> extensions = configLoader.getContentTypeExtensions(contentType);
		if (extensions != null && extensions.contains(fileNameExt)) {
			isAllowed = true;
		}
		return isAllowed;
	}

	@Override
	public boolean isValidStreamHeader(@Nonnull final InputStream inputStream, @Nonnull final String requestContentType,
			@Nonnull final String fileExtension) {
		boolean isValidInputStream = false;
		final String detectedFileContentType = SecureFileUtils.detectFileContentType(inputStream);
		if (detectedFileContentType != null
				&& !detectedFileContentType.equals(DEFAULT_CONTENT_TYPE_NOT_ALLOWED)
				&& configLoader.existsContentType(detectedFileContentType)
				&& isValidFileExtension(detectedFileContentType, fileExtension)
				&& isValidFileExtension(requestContentType, fileExtension)) {
			isValidInputStream = true;
		}
		return isValidInputStream;
	}
}
