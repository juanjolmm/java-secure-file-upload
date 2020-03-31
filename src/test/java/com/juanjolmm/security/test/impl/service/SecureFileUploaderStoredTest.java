package com.juanjolmm.security.test.impl.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.juanjolmm.security.api.service.SecureFileUploader;
import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.service.SecureFileUploaderStored;

/**
 * Test class of SecureFileUploaderStored.
 * 
 * @author jlopez1
 *
 */
public final class SecureFileUploaderStoredTest {

	private SecureFileUploader fileUploader;

	@Before
	public void init() {
		fileUploader = new SecureFileUploaderStored();
	}
	
	@Test
	public void wordUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final String initialFileName = "word.doc";
		final String requestContentType = "application/msword";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/word.doc");
		long startMilliSecs = System.currentTimeMillis();
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		long stopMilliSecs = System.currentTimeMillis();
        System.out.println("Stored impl took: " + ((stopMilliSecs - startMilliSecs)) + " ms.");

	}
	
	@Test(expected = SecureFileUploadException.class)
	public void fakeWordUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final String initialFileName = "fakeWord.doc";
		final String requestContentType = "application/msword";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/fakeWord.doc");
		long startMilliSecs = System.currentTimeMillis();
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		long stopMilliSecs = System.currentTimeMillis();
        System.out.println("Stored impl took: " + ((stopMilliSecs - startMilliSecs)) + " ms.");
	}

	@Test
	public void uploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final String initialFileName = "real.png";
		final String requestContentType = "image/png";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/real.png");
		long startMilliSecs = System.currentTimeMillis();
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		long stopMilliSecs = System.currentTimeMillis();
        System.out.println("Stored impl took: " + ((stopMilliSecs - startMilliSecs)) + " ms.");
	}

	@Test
	public void validImageUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final String initialFileName = "Tulips.jpg";
		final String requestContentType = "image/jpeg";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/Tulips.jpg");
		long startMilliSecs = System.currentTimeMillis();
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		long stopMilliSecs = System.currentTimeMillis();
        System.out.println("Stored impl took: " + ((stopMilliSecs - startMilliSecs)) + " ms.");
	}

	@Test(expected = SecureFileUploadException.class)
	public void notRealPNGTUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final String initialFileName = "Tulips.png";
		final String requestContentType = "image/png";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/TulisFakePNG.png");
		long startMilliSecs = System.currentTimeMillis();
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		long stopMilliSecs = System.currentTimeMillis();
        System.out.println("Stored impl took: " + ((stopMilliSecs - startMilliSecs)) + " ms.");
	}

	@Test(expected = SecureFileUploadException.class)
	public void noEXTUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final String initialFileName = "real2";
		final String requestContentType = "image/png";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/real2.png");
		long startMilliSecs = System.currentTimeMillis();
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		long stopMilliSecs = System.currentTimeMillis();
        System.out.println("Stored impl took: " + ((stopMilliSecs - startMilliSecs)) + " ms.");
	}
}
