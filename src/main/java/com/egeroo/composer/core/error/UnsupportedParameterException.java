package com.egeroo.composer.core.error;

import java.util.List;

import org.springframework.http.HttpStatus;

public class UnsupportedParameterException extends CoreException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<String> unsupportedParameters;

    public UnsupportedParameterException(final List<String> unsupportedParameters) {
        this.unsupportedParameters = unsupportedParameters;
        this.setMessage("Unsupported Parameters");
        this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        this.setError(unsupportedParameters);
    }

    public List<String> getUnsupportedParameters() {
        return this.unsupportedParameters;
    }
}