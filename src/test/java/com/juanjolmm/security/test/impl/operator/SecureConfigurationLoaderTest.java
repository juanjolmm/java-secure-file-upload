package com.juanjolmm.security.test.impl.operator;

import org.junit.Assert;
import org.junit.Test;

import com.juanjolmm.security.api.FileNameModeEnum;
import com.juanjolmm.security.exception.SecureFileUploadException;
import com.juanjolmm.security.impl.operator.SecureConfigurationLoaderImpl;

/**
 * Test class of SecureConfigurationLoader.
 * 
 * @author jlopez1
 *
 */
public final class SecureConfigurationLoaderTest {

	private SecureConfigurationLoaderImpl secureConfigurationLoader;

	@Test
	public void secureConfigurationLoaderConstructorTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
	}

	@Test
	public void validExistsContentTypeTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
		Assert.assertTrue(secureConfigurationLoader.existsContentType("image/png"));
	}

	@Test
	public void notValidExistsContentTypeTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
		Assert.assertFalse(secureConfigurationLoader.existsContentType("image/pgg"));
	}

	@Test
	public void validGetContentTypeExtensionsTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
		Assert.assertNotNull(secureConfigurationLoader.getContentTypeExtensions("image/png"));
	}

	@Test
	public void notValidGetContentTypeExtensionsTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
		Assert.assertNull(secureConfigurationLoader.getContentTypeExtensions("image/pgg"));
	}

	@Test
	public void getMaxFileSizeTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
		Assert.assertEquals(10 * 1024 * 1024, secureConfigurationLoader.getMaxFileSize());
	}
	
	@Test
	public void getDefaultStorageFolderTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
		Assert.assertEquals("uploadedfiles", secureConfigurationLoader.getStorageFolder());
	}
	
	@Test
	public void getFileNameModeTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
		Assert.assertEquals(FileNameModeEnum.SECURE, secureConfigurationLoader.getFileNameMode());
	}
	
	@Test
	public void getSecureOwnerTest() throws SecureFileUploadException {
		secureConfigurationLoader = new SecureConfigurationLoaderImpl();
		Assert.assertNull(secureConfigurationLoader.getSecureFileOwner());
	}
}
