package com.juanjolmm.security.impl.operator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juanjolmm.security.api.FileNameModeEnum;
import com.juanjolmm.security.api.operator.SecureConfigurationLoader;

/**
 * SecureConfigurationLoader implementation used to load and store the
 * configuration properties.
 * 
 * @author jlopez1
 *
 */
public final class SecureConfigurationLoaderImpl implements SecureConfigurationLoader {

	private static Logger LOGGER = LoggerFactory.getLogger(SecureConfigurationLoaderImpl.class);
	@Nonnull
	private final Map<String, List<String>> contentTypeExtensionsWhiteList;
	@Nonnull
	private static final String SECURE_PROPERTIES_CONFIG_FILE = "secure-file-upload_config.properties";
	@Nonnull
	private static final String SECURE_PROPERTIES_WHITELIST_FILE = "secure-file-upload_whitelist.properties";
	@Nonnull
	private static final String MAX_FILE_SIZE_PROPERTY = "max.file.size";
	@Nonnull
	private static final String DEFAULT_STORAGE_FOLDER_PROPERTY = "default.storage.folder";
	@Nonnull
	private static final String FILE_NAME_MODE_PROPERTY = "file.name.mode";
	@Nonnull
	private static final String SECURE_FILE_OWNER_PROPERTY = "secure.file.owner";
	private long maxFileSize = 10 * 1024 * 1024;
	@Nonnull
	private String defaultStorageFolder = "uploadedfiles";
	@Nonnull
	private FileNameModeEnum fileNameMode = FileNameModeEnum.SECURE;
	@Nullable
	private String secureFileOwner;

	/**
	 * Default constructor. This constructor initialize all the configuration
	 * variables
	 */
	public SecureConfigurationLoaderImpl() {
		loadConfigValues();
		contentTypeExtensionsWhiteList = new HashMap<String, List<String>>();
		loadWhiteListValues();
	}

	private void loadConfigValues() {
		try {
			final InputStream configStream = getResource(SECURE_PROPERTIES_CONFIG_FILE);
			final Properties configProperties = new Properties();
			configProperties.load(configStream);
			initMaxFileSize(configProperties);
			initStorageFolder(configProperties);
			initFileNameMode(configProperties);
			initSecureFileOwner(configProperties);
		} catch (IOException e) {
			LOGGER.warn(
					"Config file not found. Loading all configuration parameters with default value. "
							+ e.getMessage());
		}
	}

	private void loadWhiteListValues() {
		try {
			final InputStream whiteListStream = getResource(SECURE_PROPERTIES_WHITELIST_FILE);
			final Properties whitelistProperties = new Properties();
			whitelistProperties.load(whiteListStream);
			initWhiteList(whitelistProperties);
		} catch (IOException e) {
			LOGGER.warn(
					"White list file not found. Non Content-type allowed. " + e.getMessage());
		}
	}

	private InputStream getResource(@Nonnull final String resourceName) throws IOException {
		final InputStream inputStream;
		final ClassLoader classLoader = getClass().getClassLoader();
		final URL resourceURL = classLoader.getResource(resourceName);
		if (resourceURL != null) {
			inputStream = resourceURL.openStream();
		} else {
			throw new IOException("Resource " + resourceName + " not found.");
		}
		return inputStream;
	}

	private void initWhiteList(@Nonnull final Properties properties) {
		Set<Object> contentTypes = properties.keySet();
		for (Object contentType : contentTypes) {
			final String currentContentType = ((String) contentType).trim();
			final List<String> allowedContentTypeExtensions = getListValuesFromProperties(properties,
					currentContentType);
			if (allowedContentTypeExtensions.size() > 0) {
				contentTypeExtensionsWhiteList.put(currentContentType, allowedContentTypeExtensions);
			} else {
				LOGGER.warn("Extensions not configured for current Content-type. Content-type not permitted.");
			}
		}
	}

	@Nonnull
	private List<String> getListValuesFromProperties(@Nonnull final Properties properties,
			@Nonnull final String property) {
		List<String> values = new ArrayList<String>();
		final String stringValue = getTrimedConfigProperty(properties, property);
		if (stringValue != null) {
			values = getTrimedValues(stringValue);
		}
		return values;
	}

	@Nullable
	private String getTrimedConfigProperty(@Nonnull final Properties properties, @Nonnull final String property) {
		String stringValue = properties.getProperty(property);
		if (stringValue == null || stringValue.trim().length() == 0) {
			stringValue = null;
		} else {
			stringValue = stringValue.trim();
		}
		return stringValue;
	}

	@Nonnull
	private List<String> getTrimedValues(@Nonnull final String stringValue) {
		return Arrays.asList(stringValue.split(",")).stream().map(ext -> ext.trim()).collect(Collectors.toList());
	}

	private void initMaxFileSize(@Nonnull final Properties properties) {
		final String stringValue = getTrimedConfigProperty(properties, MAX_FILE_SIZE_PROPERTY);
		try {
			if (stringValue != null) {
				maxFileSize = Long.valueOf(stringValue);
			}
		} catch (NumberFormatException e) {
			LOGGER.warn("Error while seting up maximum file size. Default size established.");
		}
	}

	private void initStorageFolder(@Nonnull final Properties properties) {
		final String stringValue = getTrimedConfigProperty(properties, DEFAULT_STORAGE_FOLDER_PROPERTY);
		if (stringValue != null) {
			defaultStorageFolder = stringValue;
		} else {
			LOGGER.warn("Default storage folder not configured. Default folder established.");
		}
	}

	private void initFileNameMode(@Nonnull final Properties properties) {
		final String stringValue = getTrimedConfigProperty(properties, FILE_NAME_MODE_PROPERTY);
		if (stringValue != null) {
			switch (stringValue) {
			case "ORIGINAL":
				fileNameMode = FileNameModeEnum.ORIGINAL;
				break;
			case "SECURE":
				fileNameMode = FileNameModeEnum.SECURE;
				break;
			default:
				fileNameMode = FileNameModeEnum.SECURE;
			}
		} else {
			LOGGER.warn("File name mode not configured. Default SECURE mode established.");
		}
	}

	private void initSecureFileOwner(@Nonnull final Properties properties) {
		final String stringValue = getTrimedConfigProperty(properties, SECURE_FILE_OWNER_PROPERTY);
		if (stringValue != null) {
			secureFileOwner = stringValue;
		} else {
			LOGGER.warn("Secure file owner not configured. Owner not established.");
		}
	}

	@Override
	public boolean existsContentType(@Nonnull final String contentType) {
		return contentTypeExtensionsWhiteList.get(contentType) != null;
	}

	@Override
	@Nullable
	public List<String> getContentTypeExtensions(@Nonnull final String contentType) {
		return contentTypeExtensionsWhiteList.get(contentType);
	}

	@Override
	public long getMaxFileSize() {
		return maxFileSize;
	}

	@Override
	@Nonnull
	public String getStorageFolder() {
		return defaultStorageFolder;
	}

	@Override
	@Nonnull
	public FileNameModeEnum getFileNameMode() {
		return fileNameMode;
	}

	@Override
	@Nullable
	public String getSecureFileOwner() {
		return secureFileOwner;
	}

}
