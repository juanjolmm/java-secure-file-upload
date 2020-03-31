package com.juanjolmm.security.api.operator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Nonnull;

import com.juanjolmm.security.exception.SecureFileUploadException;

/**
 * Interface to define secure operations with files.
 * 
 * @author jlopez1
 */
public interface SecureFileOperator {

	/**
	 * Method that create a new file in a configurable secure storage. The final
	 * file name depends on the implementation. It s recommended that the final
	 * name be different from the user original file name. Also is recommended
	 * that the secure storage be outside the document root.
	 * 
	 * @param inputStream
	 *            The source input stream.
	 * @param initialFileName
	 *            The user original name of the file.
	 * @return A new file created in the secure storage folder.
	 * @throws SecureFileUploadException
	 *             If failed the creation of the new file.
	 */
	@Nonnull
	public File createNewFileInSecureStorage(@Nonnull final InputStream inputStream,
			@Nonnull final String initialFileName) throws SecureFileUploadException;

	/**
	 * Method to set the current file owner.
	 * 
	 * @param file
	 *            The file whom owner should be set.
	 * @throws SecureFileUploadException
	 *             If the change file owner operation failed.
	 */
	public void setSecureFileOwner(@Nonnull final File file) throws SecureFileUploadException;

	/**
	 * Method to establish the secure permissions of a file. The file should not
	 * have execute permission.
	 * 
	 * @param file
	 *            The file whose permissions should change.
	 * @throws SecureFileUploadException
	 *             If the permissions change operation failed.
	 */
	public void setSecureFilePermissions(@Nonnull final File file) throws SecureFileUploadException;

	/**
	 * Method to get a file list with all the uploaded files.
	 * 
	 * @return A list with all the uploaded files. Empty list if no files found.
	 */
	@Nonnull
	public List<File> listUploadedFiles();

	/**
	 * Method to get a file list with all the uploaded files.
	 * 
	 * @param fileName
	 *            The name of the file to retrieve.
	 * @return The file with the name provided.
	 * @throws FileNotFoundException
	 *             If the file does not exist.
	 */
	@Nonnull
	public File getUplodadedFile(@Nonnull final String fileName) throws FileNotFoundException;
}
