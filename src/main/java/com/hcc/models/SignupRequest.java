package com.hcc.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupRequest {
	String firstName;
	String lastName;
	String email;
	String username;
	List<Map<String, String>> credentials;
	List<String> realmRoles;
	
	public SignupRequest(String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		
		Map<String, String> map = new HashMap<>();
		map.put("type", "password");
		map.put("value", password);
		map.put("temporary", "false");
		
		this.credentials = new ArrayList<>();
		this.credentials.add(map);
		
		realmRoles = new ArrayList<>();
		realmRoles.add("user");
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public List<Map<String, String>> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<Map<String, String>> credentials) {
		this.credentials = credentials;
	}

	public List<String> getRealmRoles() {
		return realmRoles;
	}

	public void setRealmRoles(List<String> realmRoles) {
		this.realmRoles = realmRoles;
	}
}
	