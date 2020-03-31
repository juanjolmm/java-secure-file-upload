package com.juanjolmm.security.api.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Nonnull;

import com.juanjolmm.security.exception.SecureFileUploadException;

/**
 * Interface to define secure file uploads operations.
 * 
 * @author jlopez1
 *
 */
public interface SecureFileUploader {

	/**
	 * Method to upload and store a new file in a secure way.
	 * 
	 * @param inputStream
	 *            Request original input stream.
	 * @param fileName
	 *            The original user file name.
	 * @param requestContentType
	 *            The request Content-type.
	 * @return The new created file.
	 * @throws SecureFileUploadException
	 *             If the file is not allowable to be uploaded and stored.
	 */
	@Nonnull
	public File upload(@Nonnull final InputStream inputStream, @Nonnull final String fileName,
			@Nonnull final String requestContentType) throws SecureFileUploadException;

	/**
	 * Method to get a file list with all the uploaded files.
	 * 
	 * @return A list with all the uploaded files. Empty list if no files found.
	 */
	@Nonnull
	public List<File> list();

	/**
	 * Method to get the file with the provided name.
	 * 
	 * @param fileName
	 *            The name of the file to retrieve.
	 * @return The file with the provided name
	 * @throws FileNotFoundException
	 *             If the file does not exist.
	 * @throws SecureFileUploadException
	 *             If the content-type or extension of the file is not allowed.
	 */
	@Nonnull
	public File get(@Nonnull final String fileName) throws FileNotFoundException, SecureFileUploadException;
}
