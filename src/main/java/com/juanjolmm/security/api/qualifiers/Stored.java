package com.juanjolmm.security.api.qualifiers;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Qualifier to identify the Stored implementation.
 * 
 * @author jlopez1
 *
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD, TYPE, PARAMETER })
public @interface Stored {

}
