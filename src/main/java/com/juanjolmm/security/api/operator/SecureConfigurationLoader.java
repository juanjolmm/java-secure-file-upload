package com.juanjolmm.security.api.operator;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.juanjolmm.security.api.FileNameModeEnum;

/**
 * Interface to define operations to load the configuration properties.
 * 
 * @author jlopez1
 *
 */
public interface SecureConfigurationLoader {
	/**
	 * Method to determine if a Content-type is configured and allowed.
	 * 
	 * @param contentType
	 *            The content type to check.
	 * @return True if the content type is allowed, false in other case.
	 */
	public boolean existsContentType(@Nonnull final String contentType);

	/**
	 * Method to get the extensions associated to the provided content type.
	 * 
	 * @param contentType
	 *            The content type to check.
	 * @return The list of extensions associated to that content type or null.
	 */
	@Nullable
	public List<String> getContentTypeExtensions(@Nonnull final String contentType);

	/**
	 * Method to get the maximum file size.
	 * 
	 * @return The maximum file size.
	 */
	public long getMaxFileSize();

	/**
	 * Method to get the storage folder.
	 * 
	 * @return The storage folder string.
	 */
	@Nonnull
	public String getStorageFolder();

	/**
	 * Method to get the file name mode. ORIGINAL: To store the file with the
	 * original provided user name. SECURE: To store the file with a new and
	 * unique file name.
	 * 
	 * @return The file name mode.
	 */
	@Nonnull
	public FileNameModeEnum getFileNameMode();

	/**
	 * Method to get the name of the user to be owner of the new files.
	 * 
	 * @return The storage folder string.
	 */
	@Nullable
	public String getSecureFileOwner();

}
