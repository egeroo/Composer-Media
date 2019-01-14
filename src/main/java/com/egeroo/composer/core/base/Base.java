package com.egeroo.composer.core.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Base<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private Integer createdBy;
	private Integer updatedBy;
	private Date createdTime;
	private Date updatedTime;
	
	public T build(String json, Class<T> objectClass) {
		Gson g = new Gson();
		T object = g.fromJson(json, objectClass);
		return object;
	}
	
	public List<T> buildCollection(String json, Class<T> objectClass) {
		ObjectMapper mapper = new ObjectMapper();
		Gson g = new Gson();
		List<T> object = g.fromJson(json, mapper.getTypeFactory().constructCollectionType(List.class, objectClass));
		return object;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
