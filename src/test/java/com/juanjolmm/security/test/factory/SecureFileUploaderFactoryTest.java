package com.juanjolmm.security.test.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.juanjolmm.security.api.service.SecureFileUploader;
import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.factory.SecureFileUploaderFactory;

/**
 * Test class of SecureFileUploaderFactory.
 * 
 * @author jlopez1
 *
 */
public final class SecureFileUploaderFactoryTest {
	
	@Test
	public void uploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		//Use this method to tests new file formats:
		//Just need to put file extension and requestContent-type
		//REQ: filename = extension.extension
		//Just change these two vars
		final String extensionToTest = "m4a";
		final String requestContentType = "audio/mp4";
		//Now execute.
		final String initialFileName = extensionToTest + "." + extensionToTest;
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final InputStream inputStream = new FileInputStream("src/test/resources/files/" + initialFileName);
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void wordUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "word.doc";
		final String requestContentType = "application/msword";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/word.doc");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void word2UploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "word2.docx";
		final String requestContentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/word2.docx");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void excel1UploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "excel1.xls";
		final String requestContentType = "application/vnd.ms-excel";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/excel1.xls");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void excel2UploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "excel2.xlsx";
		final String requestContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/excel2.xlsx");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void imageUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "Tulips.jpg";
		final String requestContentType = "image/jpeg";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/Tulips.jpg");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void imagePngUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "real.png";
		final String requestContentType = "image/png";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/real.png");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void pdfUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "pdf.pdf";
		final String requestContentType = "application/pdf";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/pdf.pdf");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void txtUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "txt.txt";
		final String requestContentType = "text/plain";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/txt.txt");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}

	@Test
	public void zipUploadAndStoreFileTest() throws SecureFileUploadException, FileNotFoundException {
		final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();
		final String initialFileName = "zip.zip";
		final String requestContentType = "application/zip";
		final InputStream inputStream = new FileInputStream("src/test/resources/files/zip.zip");
		final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);
		Assert.assertNotNull(newFile);
		final List<File> list = fileUploader.list();
		Assert.assertTrue(list.size() > 0);
		final File file = fileUploader.get(newFile.getName());
		Assert.assertNotNull(file);
	}
}
