package com.egeroo.composer.core.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RequestWrapper {
	private HttpServletRequest request;
	private String jsonBody;
	private Scanner scanner;
	private Map<String, Object> parameters;
	
	public RequestWrapper(HttpServletRequest request) {
		this.request = request;
		this.jsonBody = null;
		this.parameters = new HashMap<>();
		if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())
				 || "DELETE".equalsIgnoreCase(request.getMethod())) {
	        Scanner s = null;
	        try {
	            scanner = new Scanner(request.getInputStream(), "UTF-8");
				s = scanner.useDelimiter("\\A");
		        if (s.hasNext()) {
		        	this.jsonBody = s.next();
		        } else {
		        	this.jsonBody = "";
		        }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public String getTenant() {
		if (request != null) {
			return request.getHeader("tenantID");
		}
		return null;
	}
	
	public String getJsonBody() {
		return jsonBody;
	}
	
	public String getJsonBody(List<String> selectors) {
		JsonParser parser = new JsonParser();
		JsonObject result = (JsonObject) parser.parse(jsonBody);
		for (String selector: selectors) {
			result = result.getAsJsonObject(selector);
		}
		return result.toString();
	}
	
	public String getResource(String name) {
		String parameter = request.getParameter(name);
		return parameter;
	}
	
	public MultipartFile getFile(String name) {
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) this.request;
		return multiRequest.getFile(name);
	}
	
	public String getMethod() {
		if (request != null) {
			return request.getMethod();
		}
		return null;
	}
	
	public RequestWrapper addIdentifier(String identifierName, Object identifierValue) {
		if(this.parameters == null) {
			this.parameters = new HashMap<>();
		}
		this.parameters.put(identifierName, identifierValue);
		return this;
	}

	public Object getIdentifier(String identifierName) {
		return this.parameters.get(identifierName);
	}
	
	public String getHeader(String headerName) {
		if (request != null) {
			return request.getHeader(headerName);
		}
		return null;
	}
}
