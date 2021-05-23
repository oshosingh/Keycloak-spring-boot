package com.hcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hcc.models.LoginDto;

@RestController
public class KeycloakController {
	
	@Autowired
	RestTemplate restTemplate;

	@PostMapping("/login")
	ResponseEntity<String> login(@ModelAttribute LoginDto loginDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username", loginDto.getUsername());
		map.add("password", loginDto.getPassword());
		map.add("client_id", "spring-auth");
		map.add("grant_type", "password");
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		return restTemplate.exchange("http://localhost:8080/auth/realms/spring-auth/protocol/openid-connect/token",
				HttpMethod.POST, entity, String.class);
		
	}
	
}
