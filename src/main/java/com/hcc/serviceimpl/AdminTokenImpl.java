package com.hcc.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hcc.models.AdminTokenModel;
import com.hcc.service.AdminToken;

@Service
public class AdminTokenImpl implements AdminToken{
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public String getAdminToken() {
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
		
		return model.getBody().getAccess_token();
	}

}
