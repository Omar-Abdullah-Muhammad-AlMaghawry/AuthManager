package com.zfinance.authmanager.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
}
