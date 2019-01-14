package com.egeroo.composer.core.error;

import org.springframework.http.HttpStatus;

public class CoreException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected HttpStatus status;
	protected String message;
	protected Object error;
	
	public CoreException() {
		//
	}
	
	public CoreException(HttpStatus status, String message) {
		this.message = message;
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}
}