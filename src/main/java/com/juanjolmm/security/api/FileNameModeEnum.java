package com.juanjolmm.security.api;

/**
 * Enumeration to define the available modes to create a new file name.
 * 
 * @author jlopez1
 *
 */
public enum FileNameModeEnum {
	/**
	 * To store the file with a original based user file name.
	 */
	ORIGINAL,
	/**
	 * To store the file with a new and unique file name.
	 */
	SECURE;
}