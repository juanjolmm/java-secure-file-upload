package com.juanjolmm.security.exception;

/**
 * Custom exception to manage issues in secure file uploading.
 * 
 * @author jlopez1
 *
 */
public class SecureFileUploadException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_EXCEPTION_MESSAGE = "File not allowed";

	/**
	 * Default constructor.
	 */
	public SecureFileUploadException() {
		super(DEFAULT_EXCEPTION_MESSAGE);
	}

}
