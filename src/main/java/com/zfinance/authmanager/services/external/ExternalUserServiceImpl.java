package com.zfinance.authmanager.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.zfinance.authmanager.dto.requests.registration.RegisterUser;
import com.zfinance.authmanager.dto.response.user.UserRecord;

@Service
public class ExternalUserServiceImpl implements ExternalUserService {

	@Value("${user.service.url}")
	private String USER_SERVICE_URL;

	@Autowired
	private RestTemplate restTemplate;

//	@Autowired
//	private JwtRequestFilter jwtRequestFilter;

	@Override
	public void verifyUserProfileEmail(String userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Authorization", jwtRequestFilter.getToken());
		HttpEntity<Void> entity = new HttpEntity<>(null, headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(USER_SERVICE_URL
				+ "/profiles/verifyUserProfileEmail").queryParam("userId", userId);

		restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Void.class);
	}

	@Override
	public UserRecord registerUser(RegisterUser userCreateBody) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

//		headers.set("Authorization", jwtRequestFilter.getToken());
		HttpEntity<Void> entity = new HttpEntity<>(null, headers);

		// Create the request body and set the statusId
		HttpEntity<RegisterUser> requestEntity = new HttpEntity<>(userCreateBody, headers);

		ResponseEntity<UserRecord> response = restTemplate.exchange(USER_SERVICE_URL + "/users/registration",
				HttpMethod.POST, requestEntity, UserRecord.class);

		return response.getBody();
	}
}
