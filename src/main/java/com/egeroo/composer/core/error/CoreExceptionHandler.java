package com.egeroo.composer.core.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CoreExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CoreException.class)
    @ResponseBody
    public ResponseEntity<Object> internalServerErrorHandler(CoreException ex) {
        Map<String, Object> m1 = new HashMap<String, Object>();
//        m1.put("status", ex.getStatus().getReasonPhrase());
        m1.put("message", ex.getMessage());
        m1.put("error(s)", ex.getError());
        return new ResponseEntity<Object>(m1, ex.getStatus());
    }
    
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ValidationError error = ValidationErrorBuilder.fromBindingErrors(exception.getBindingResult());
    	return super.handleExceptionInternal(exception, error, headers, status, request);
    }
}