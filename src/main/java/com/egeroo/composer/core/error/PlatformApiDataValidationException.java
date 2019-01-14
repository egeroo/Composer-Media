package com.egeroo.composer.core.error;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when problem with an API request to the platform.
 */
public class PlatformApiDataValidationException extends CoreException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String globalisationMessageCode;
    private final String defaultUserMessage;
    private final List<ApiParameterError> errors;

    public PlatformApiDataValidationException(final List<ApiParameterError> errors) {
        super();
//    	String message = "Validation errors exist.";
//        for (ApiParameterError error : errors) {
//        	message += '\n' + error.getDefaultUserMessage();
//        }
        this.globalisationMessageCode = "validation.msg.validation.errors.exist";
        this.defaultUserMessage = "Validation errors exist.";
        this.errors = errors;
        this.setMessage(this.defaultUserMessage);
        this.setError(this.errors);
        this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        
    }

    public PlatformApiDataValidationException(final String globalisationMessageCode, final String defaultUserMessage,
            final List<ApiParameterError> errors) {
        this.globalisationMessageCode = globalisationMessageCode;
        this.defaultUserMessage = defaultUserMessage;
        this.errors = errors;
        this.setMessage(this.defaultUserMessage);
        this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        this.setError(this.errors);
    }

    public String getGlobalisationMessageCode() {
        return this.globalisationMessageCode;
    }

    public String getDefaultUserMessage() {
        return this.defaultUserMessage;
    }

    public List<ApiParameterError> getErrors() {
        return this.errors;
    }
}
