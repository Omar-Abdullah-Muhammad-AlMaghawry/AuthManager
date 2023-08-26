package com.zfinance.authmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfinance.authmanager.dto.requests.login.LoginRequestDto;
import com.zfinance.authmanager.services.SecurityService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private SecurityService securityService;

	@PostMapping(value = "/authorization")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDto loginRequestDto) {
		try {
			return ResponseEntity.ok(securityService.login(loginRequestDto));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping(value = "/validateToken")
	public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization") String token) {
		try {
			return ResponseEntity.ok(securityService.getAuthenticatedUser());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
}
