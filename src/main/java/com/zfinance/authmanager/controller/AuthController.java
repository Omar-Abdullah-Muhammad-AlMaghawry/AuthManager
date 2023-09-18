package com.zfinance.authmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zfinance.authmanager.dto.requests.signin.LoginRequestDto;
import com.zfinance.authmanager.dto.requests.signin.PasswordRecoveryConfirmBody;
import com.zfinance.authmanager.dto.requests.signin.PasswordRecoveryDto;
import com.zfinance.authmanager.dto.response.signin.AuthData;
import com.zfinance.authmanager.dto.response.signin.AuthorizationResponse;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.User;
import com.zfinance.authmanager.services.SecurityService;
import com.zfinance.authmanager.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDto loginRequestDto) {
		try {
			return ResponseEntity.ok(securityService.login(loginRequestDto));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/authorization")
	public AuthorizationResponse authorization(@RequestBody LoginRequestDto loginRequestDto) throws BusinessException {
		try {
			AuthorizationResponse authorizationResponse = new AuthorizationResponse();
			AuthData authData = securityService.authorization(loginRequestDto);
			List<AuthData> member = new ArrayList<>();
			member.add(authData);
			authorizationResponse.setMembers(member);
			return authorizationResponse;
		} catch (Exception e) {
			throw new BusinessException("error_unauthorized");
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

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			userService.saveUser(user);
			return ResponseEntity.ok("label_registerSuccessed");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/confirmAccount", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
		try {
			userService.confirmEmail(confirmationToken);
			return ResponseEntity.ok("Confirmed");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/password/recovery")
	public ResponseEntity<?> passwordRecovery(@RequestBody PasswordRecoveryDto passwordRecoveryDto) {
		try {
			userService.passwordRecovery(passwordRecoveryDto.getLogin());
			return ResponseEntity.ok("label_sentOtpSuccessed");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("password/recovery/confirm")
	public ResponseEntity<?> passwordRecoveryConfirm(
			@RequestBody PasswordRecoveryConfirmBody passwordRecoveryConfirmBody) {
		try {
			userService.passwordRecoveryConfirm(passwordRecoveryConfirmBody);
			return ResponseEntity.ok("Recovery Successed");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getUserIdFromToken")
	public ResponseEntity<?> getUserIdFromToken(@RequestParam String token) {
		try {
			User user = userService.getUserFromToken(token);
			return ResponseEntity.ok(user.getId());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
