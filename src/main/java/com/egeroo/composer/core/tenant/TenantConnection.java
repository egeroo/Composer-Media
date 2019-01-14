package com.egeroo.composer.core.tenant;

import java.io.Serializable;

public class TenantConnection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String schema_server;
    private String schema_name;
    private int schema_server_port;
    private String schema_username;
    private String schema_password;
    private Tenant tenant;
    private String identifier;
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSchema_server() {
		return schema_server;
	}
	
	public void setSchema_server(String schema_server) {
		this.schema_server = schema_server;
	}
	
	public String getSchema_name() {
		return schema_name;
	}
	
	public void setSchema_name(String schema_name) {
		this.schema_name = schema_name;
	}
	
	public int getSchema_server_port() {
		return schema_server_port;
	}
	
	public void setSchema_server_port(int schema_server_port) {
		this.schema_server_port = schema_server_port;
	}
	
	public String getSchema_username() {
		return schema_username;
	}
	
	public void setSchema_username(String schema_username) {
		this.schema_username = schema_username;
	}
	
	public String getSchema_password() {
		return schema_password;
	}
	
	public void setSchema_password(String schema_password) {
		this.schema_password = schema_password;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
    
}