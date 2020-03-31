package com.juanjolmm.security.test.impl.operator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.operator.SecureConfigurationLoaderImpl;
import com.juanjolmm.security.impl.operator.SecureRequestOperatorImpl;

/**
 * Test class of SecureRequestOperatorImpl.
 * 
 * @author jlopez1
 *
 */
public final class SecureRequestOperatorTest {

	private SecureRequestOperatorImpl requestOperator;

	@Before
	public void init() {
		SecureConfigurationLoaderImpl loader = new SecureConfigurationLoaderImpl();
		requestOperator = new SecureRequestOperatorImpl(loader);
	}
	
	@Test
	public void checkTikaHeader() throws FileNotFoundException, SecureFileUploadException {
		final String extensionToTest = "ogg";
		final String requestContentType = "application/ogg";
		final String fileName = extensionToTest + "." + extensionToTest;
		InputStream inputStream = new FileInputStream("src/test/resources/files/" + fileName);
		Assert.assertTrue(requestOperator.isValidStreamHeader(inputStream, requestContentType, extensionToTest));
	}

	@Test
	public void validRequestContentTypeTest() {
		Assert.assertTrue(requestOperator.isValidRequestContentType("image/png"));
	}

	@Test
	public void notValidRequestContentTypeTest() {
		Assert.assertFalse(requestOperator.isValidRequestContentType("image/pnn"));
	}

	@Test
	public void validFileExtensionTest() {
		Assert.assertTrue(requestOperator.isValidFileExtension("image/png", "png"));
	}

	@Test
	public void notValidFileExtensionTest() {
		Assert.assertFalse(requestOperator.isValidFileExtension("image/png", "exe"));
	}

	@Test
	public void isValidFileContentTypeTest() throws FileNotFoundException, SecureFileUploadException {
		final String extensionToTest = "png";
		final String requestContentType = "image/png";
		InputStream inputStream = new FileInputStream("src/test/resources/files/real.png");
		Assert.assertTrue(requestOperator.isValidStreamHeader(inputStream, requestContentType, extensionToTest));
	}

	@Test
	public void batnotValidFileContentTypeTest() throws FileNotFoundException, SecureFileUploadException {
		final String extensionToTest = "bat";
		final String requestContentType = "image/png";
		InputStream inputStream = new FileInputStream("src/test/resources/files/executable.bat");
		Assert.assertFalse(requestOperator.isValidStreamHeader(inputStream, requestContentType, extensionToTest));
	}
}
