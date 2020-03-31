package com.juanjolmm.security.test.impl.util;

import org.junit.Test;

import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.util.SecureFileUtils;

/**
 * Test class of SecureFileUtils.
 * 
 * @author jlopez1
 *
 */
public final class SecureFileUtilsTest {

	@Test
	public void getOriginalBasedFileNameTest() throws SecureFileUploadException {
		final String initialFileName = "initialName.txt";
		final String originalBasedFileName = SecureFileUtils.getOriginalBasedFileName(initialFileName);
		System.out.println(originalBasedFileName);
	}
	
	@Test
	public void getSecureFileNameNameTest() throws SecureFileUploadException {
		final String initialFileName = "wwwwwwwwwwwwwwwwwwwwwwwwwwwwww.1111";
		final String secureFileName = SecureFileUtils.getSecureFileName(initialFileName);
		System.out.println(secureFileName);
	}
	
	@Test
	public void getFileExtensionTest() throws SecureFileUploadException {
		final String initialFileName = "initialName.txt";
		final String fileExtension = SecureFileUtils.getFileExtension(initialFileName);
		System.out.println(fileExtension);
	}
}
