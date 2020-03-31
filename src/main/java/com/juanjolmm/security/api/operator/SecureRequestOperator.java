package com.juanjolmm.security.api.operator;

import java.io.InputStream;

import javax.annotation.Nonnull;

/**
 * Interface that defines secure operations with streams for upload requests.
 * 
 * @author jlopez1
 *
 */
public interface SecureRequestOperator {

	/**
	 * Method to check if the request Content-Type is allowed.
	 * 
	 * @param requestContentType
	 *            The Content-Type of the request.
	 * @return True if the request content type is allowed, false in other case.
	 */
	public boolean isValidRequestContentType(@Nonnull final String requestContentType);

	/**
	 * Method to check if the file extension is allowed for the allowed
	 * Content-Type.
	 * 
	 * @param contentType
	 *            The allowed content type.
	 * @param fileExtension
	 *            The extension to be validated for the Content-type.
	 * @return True if the extension is valid for the provided Content type,
	 *         false in other case.
	 */
	public boolean isValidFileExtension(@Nonnull final String contentType, @Nonnull final String fileExtension);

	/**
	 * Method to check if provided input stream belongs to the provided content
	 * type.
	 * 
	 * @param inputStream
	 *            The input stream to be checked
	 * @param requestContentType
	 *            The Content-Type of the request.
	 * @param fileExtension
	 *            The extension to be validated for the Content-type.
	 * @return True if input stream belongs to the provided content type, false
	 *         in other case.
	 */
	public boolean isValidStreamHeader(@Nonnull final InputStream inputStream, @Nonnull final String requestContentType,
			@Nonnull final String fileExtension);
}
