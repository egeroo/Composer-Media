package com.egeroo.composer.core.error;

import org.springframework.http.HttpStatus;

public class InvalidJsonException extends CoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	
	public InvalidJsonException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Json");
	}
}
