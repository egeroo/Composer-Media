package com.egeroo.composer.core.response;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class ApiResponse {
	private int status;
	private Integer entityId;
	private Map<String, Object> fields;
	private Object entity;
	
	public ApiResponse() {
		fields = new HashMap<>();
		status = 1;
	}
	
	public ApiResponse statusSuccess() {
		status = 1;
		return this;
	}
	
	public ApiResponse statusFailed() {
		status = 0;
		return this;
	}
	
	public ApiResponse addField(String name, Object value) {
		fields.put(name, value);
		return this;
	}
	
	public ApiResponse withEntity(Object entity) {
		this.entity = entity;
		return this;
	}
	
	public ApiResponse withEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}
	
	public String build() {
		Gson gson = new Gson();
		if(this.entity != null) {
			return gson.toJson(this.entity);
		} else {
			Map<String, Object> toBuild = new HashMap<>(this.fields);
			if(status == 1) {
				toBuild.put("status", "success.");
			} else {
				toBuild.put("status", "failed.");
			}
			if(entityId != null) {
				toBuild.put("id", entityId);
			}
			return gson.toJson(toBuild);
		}
	}
}
