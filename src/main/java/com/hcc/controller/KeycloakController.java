package com.hcc.controller;

import java.util.Collections;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcc.models.AdminTokenModel;
import com.hcc.models.LoginDto;
import com.hcc.models.SignUpDto;
import com.hcc.models.SignupRequest;
import com.hcc.service.AdminToken;

@RestController
public class KeycloakController {

	private static Logger logger = LoggerFactory.getLogger(KeycloakController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	AdminToken adminToken;

	@Value("${prop.keycloak.server.uri}")
	String keyCloakServerUri;

	@Value("${prop,keycloak.login.uri}")
	String keyCloakLoginUri;

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
		return restTemplate.exchange(keyCloakServerUri + keyCloakLoginUri, HttpMethod.POST, entity, String.class);

	}

	@GetMapping("/admin/token")
	public ResponseEntity<AdminTokenModel> token() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", "admin-cli");
		map.add("client_secret", "11474c12-9b52-4a25-a01f-a7ac72668702");
		map.add("grant_type", "client_credentials");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<AdminTokenModel> model = restTemplate.exchange(
				"http://localhost:8080/auth/realms/master/protocol/openid-connect/token", HttpMethod.POST, entity,
				AdminTokenModel.class);

		return ResponseEntity.ok().body(model.getBody());
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@ModelAttribute SignUpDto signUpDto) {
		SignupRequest request = new SignupRequest(signUpDto.getFirstName(), signUpDto.getLastName(),
				signUpDto.getEmail(), signUpDto.getUsername(), signUpDto.getPassword());

		String accessToken = adminToken.getAdminToken();
		logger.info("received token : {}", accessToken);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(accessToken);

		String jsonRequest = "";

		ObjectMapper obj = new ObjectMapper();
		try {
			jsonRequest = obj.writeValueAsString(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseEntity<?> response = null;
		//return ResponseEntity.ok().body(request);
		HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
		response = restTemplate.exchange("http://localhost:8080/auth/admin/realms/spring-auth/users",HttpMethod.POST, entity,
				String.class);
		HashMap<String, String> msg = new HashMap<>();
		if (response.getStatusCode() == HttpStatus.CREATED) {
			msg.put("msg", "user created");
		} else {
			msg.put("error msg", "failed to create user");
		}
		return ResponseEntity.status(response.getStatusCode()).body(msg);
	}

}
