package com.juanjolmm.security.impl.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;

import com.juanjolmm.security.exception.SecureFileUploadException;

/**
 * Class that have some methods to manage file names.
 * 
 * @author jlopez1
 *
 */
public final class SecureFileUtils {

	/**
	 * Regex pattern to validate file names. L: Letters; N: Numbers; Zs: Space;
	 * Pd: Dash punctuation; Pc: Connector punctuation; Min 1, Max 50
	 */
	private static final String UTF8_FILE_NAME_REGEX = "(?u)[\\p{L}\\p{N}\\p{Zs}\\p{Pd}\\p{Pc}]{1,50}";
	/**
	 * Regex pattern to validate file extensions. L: Letters; N: Numbers; Min 1,
	 * Max 4
	 */
	private static final String UTF8_FILE_EXTENSION_REGEX = "(?u)[\\p{L}\\p{N}]{1,4}";

	private static final String DEFAULT_CONTENT_TYPE_NOT_ALLOWED = "application/octet-stream";

	/**
	 * Method to create a new file name based in the original name.
	 * 
	 * @param initialFileName
	 *            the initial file name
	 * @return the original file name (if valid) concatenated with a timestamp.
	 * @throws SecureFileUploadException
	 *             If the initial name is not valid.
	 */
	@Nonnull
	public static String getOriginalBasedFileName(@Nonnull final String initialFileName)
			throws SecureFileUploadException {
		final String fileNameWithoutExtension = getValidFileNameWithoutExtension(initialFileName);
		return fileNameWithoutExtension + getFileNameCommonAppend(initialFileName);
	}

	@Nonnull
	private static String getValidFileNameWithoutExtension(@Nonnull final String fileName)
			throws SecureFileUploadException {
		final String fileNameWithoutExtension;
		if ((fileNameWithoutExtension = FilenameUtils.getBaseName(fileName)) != null
				&& fileNameWithoutExtension.matches(UTF8_FILE_NAME_REGEX)) {
			return fileNameWithoutExtension;
		} else {
			throw new SecureFileUploadException();
		}
	}

	@Nonnull
	private static String getFileNameCommonAppend(@Nonnull final String initialFileName)
			throws SecureFileUploadException {
		final String currentTimestamp = getCurrentTimestampString();
		String fileNameExtension = getFileExtension(initialFileName);
		return "_" + currentTimestamp + "." + fileNameExtension;
	}

	/**
	 * Method to create a new unique file name based on a random UUID.
	 * 
	 * @param initialFileName
	 *            the initial file name
	 * @return A new unique file name based on a random UUID.
	 * @throws SecureFileUploadException
	 *             If the initial name is not valid.
	 */
	@Nonnull
	public static String getSecureFileName(@Nonnull final String initialFileName) throws SecureFileUploadException {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid + getFileNameCommonAppend(initialFileName);
	}

	@Nonnull
	private static String getCurrentTimestampString() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
	}

	/**
	 * Method to extract the extension of a file name.
	 * 
	 * @param fileName
	 *            The file name.
	 * @return The extension of the file name.
	 * @throws SecureFileUploadException
	 *             If the extension is not valid.
	 */
	@Nonnull
	public static String getFileExtension(@Nonnull final String fileName) throws SecureFileUploadException {
		final String fileExtension;
		if ((fileExtension = FilenameUtils.getExtension(fileName)) != null
				&& fileExtension.matches(UTF8_FILE_EXTENSION_REGEX)) {
			return fileExtension;
		} else {
			throw new SecureFileUploadException();
		}
	}

	/**
	 * Method to get a byte array from the input stream provided.
	 * 
	 * @param inputStream
	 *            The source input stream.
	 * @return An array that contents all the input stream bytes.
	 * @throws SecureFileUploadException
	 *             If something fails while reading bytes.
	 */
	public static byte[] getBytesFromInputStream(@Nonnull final InputStream inputStream)
			throws SecureFileUploadException {
		try {
			return IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			throw new SecureFileUploadException();
		}

	}

	/**
	 * Method to get the mime type (or mime type) of the provided input stream.
	 * 
	 * @param inputStream
	 *            The input stream to be checked.
	 * @return The content type of the provided input stream.
	 */
	@Nonnull
	public static String detectFileContentType(@Nonnull final InputStream inputStream) {
		String detectedFileContentType;
		try {
			final Tika tika = new Tika();
			detectedFileContentType = tika.detect(TikaInputStream.get(inputStream));
		} catch (IOException e) {
			detectedFileContentType = DEFAULT_CONTENT_TYPE_NOT_ALLOWED;
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return detectedFileContentType;
	}
}
