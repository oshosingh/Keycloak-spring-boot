package com.hcc.models;

public class LoginRequest {
	String username;
	String password;
	String client_id;
	String grant_type;

	public LoginRequest(String username, String password, String client_id, String grant_type) {
		this.username = username;
		this.password = password;
		this.client_id = client_id;
		this.grant_type = grant_type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	
	
}
