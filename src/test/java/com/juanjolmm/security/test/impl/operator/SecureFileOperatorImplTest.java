package com.juanjolmm.security.test.impl.operator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.operator.SecureConfigurationLoaderImpl;
import com.juanjolmm.security.impl.operator.SecureFileOperatorImpl;

/**
 * Test class of SecureFileOperatorImpl.
 * 
 * @author jlopez1
 *
 */
public final class SecureFileOperatorImplTest {

	private SecureFileOperatorImpl fileOperator;

	@Before
	public void init() {
		SecureConfigurationLoaderImpl loader = new SecureConfigurationLoaderImpl();
		fileOperator = new SecureFileOperatorImpl(loader);
	}

	@Test
	public void createNewFileInSecureStorageTest() throws SecureFileUploadException, FileNotFoundException {
		final String initialFileName = "file1.txt";
		InputStream inputStream = new FileInputStream("src/test/resources/files/Tulips.jpg");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, initialFileName);
		Assert.assertNotNull(newFile);
	}

	@Test
	public void copyStreamToMaxSizeFileTest() throws SecureFileUploadException {
		String exampleString = "Example bytes";
		InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "file1.txt");
	}

	@Test
	public void imageCopyStreamToMaxSizeFileTest() throws SecureFileUploadException, FileNotFoundException {
		InputStream inputStream = new FileInputStream("src/test/resources/files/Tulips.jpg");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "Tulips.jpg");
	}

	@Test
	public void imageCopyStreamToMaxSizeFileTest2() throws SecureFileUploadException, FileNotFoundException {
		InputStream inputStream = new FileInputStream("src/test/resources/files/real2.png");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "real2.png");
	}

	@Test
	public void setSecureFilePermissionsTest() throws SecureFileUploadException, FileNotFoundException {
		InputStream inputStream = new FileInputStream("src/test/resources/files/executable.bat");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "executable.bat");
		fileOperator.setSecureFilePermissions(newFile);
	}

	@Test
	public void setSecureFilePermissionsTest2() throws SecureFileUploadException, FileNotFoundException {
		InputStream inputStream = new FileInputStream("src/test/resources/files/real2.png");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "real2.png");
		fileOperator.setSecureFilePermissions(newFile);
	}

	@Test
	public void maxFileSizeCopyStreamToMaxSizeFileTest() throws SecureFileUploadException, FileNotFoundException {
		// Should change max file size in properties
		InputStream inputStream = new FileInputStream("src/test/resources/files/real2.png");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "real2.png");
	}

	@Test
	public void wordCopyStreamToMaxSizeFileTest() throws SecureFileUploadException, FileNotFoundException {
		InputStream inputStream = new FileInputStream("src/test/resources/files/word.doc");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "word.doc");
	}

	@Test
	public void listUploadedFilesTest() throws FileNotFoundException, SecureFileUploadException {
		final int before = fileOperator.listUploadedFiles().size();
		InputStream inputStream = new FileInputStream("src/test/resources/files/word.doc");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "word.doc");
		List<File> listAfter = fileOperator.listUploadedFiles();
		Assert.assertTrue(listAfter.size() == before + 1);
	}
	
	@Test
	public void getUplodadedFileTest() throws SecureFileUploadException, FileNotFoundException {
		InputStream inputStream = new FileInputStream("src/test/resources/files/word.doc");
		File newFile = fileOperator.createNewFileInSecureStorage(inputStream, "word.doc");
		final File fileRetrieved = fileOperator.getUplodadedFile(newFile.getName());
		Assert.assertTrue(fileRetrieved != null);
	}
	
	@Test(expected=FileNotFoundException.class)
	public void nullGetUplodadedFileTest() throws FileNotFoundException {
		final File fileRetrieved = fileOperator.getUplodadedFile("dsddsds.esd");
	}
}
