package com.juanjolmm.security.factory;

import com.juanjolmm.security.api.service.SecureFileUploader;
import com.juanjolmm.security.impl.service.SecureFileUploaderInMemory;
import com.juanjolmm.security.impl.service.SecureFileUploaderStored;

/**
 * Factory to get a SecureFileUploader implementation.
 * 
 * @author jlopez1
 *
 */
public final class SecureFileUploaderFactory {

	/**
	 * Method to create and return the default secure file uploader object.
	 * 
	 * @return A default secure file uploader object.
	 */
	public static SecureFileUploader getDefaultSecureFileUploader() {
		return new SecureFileUploaderStored();
	}

	/**
	 * Method to create and return an stored secure file uploader object.
	 * 
	 * @return An stored secure file uploader object.
	 */
	public static SecureFileUploader getStoredSecureFileUploader() {
		return new SecureFileUploaderStored();
	}

	/**
	 * Method to create and return an in memory secure file uploader object.
	 * 
	 * @return An in memory secure file uploader object.
	 */
	public static SecureFileUploader getInMemorySecureFileUploader() {
		return new SecureFileUploaderInMemory();
	}
}
