package com.zfinance.authmanager.controller;

import java.text.ParseException;
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

import com.zfinance.authmanager.dto.requests.registration.RegisterUser;
import com.zfinance.authmanager.dto.requests.signin.LoginForOtpDto;
import com.zfinance.authmanager.dto.requests.signin.LoginRequestDto;
import com.zfinance.authmanager.dto.requests.signin.NewPasswordConfirmBody;
import com.zfinance.authmanager.dto.response.signin.AuthData;
import com.zfinance.authmanager.dto.response.signin.AuthorizationResponse;
import com.zfinance.authmanager.dto.totp.request.VerificationTotpLogin;
import com.zfinance.authmanager.dto.totp.response.VerificationResponse;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.mapper.UserMapper;
import com.zfinance.authmanager.orm.User;
import com.zfinance.authmanager.services.SecurityService;
import com.zfinance.authmanager.services.UserService;
import com.zfinance.authmanager.services.ZFinConfigService;
import com.zfinance.authmanager.services.external.ExternalUserServiceImpl;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;

	@Autowired
	private ZFinConfigService zFinConfigService;

	@Autowired
	private ExternalUserServiceImpl externalUserServiceImpl;

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

	@PostMapping("/registration")
	public ResponseEntity<?> registerUser(@RequestBody RegisterUser data) {
		try {
			externalUserServiceImpl.registerUser(data);
			userService.sendOtpForPassword(data.getLogin());
			return ResponseEntity.ok("label_registerSuccessed");
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
	public ResponseEntity<?> passwordRecovery(@RequestBody LoginForOtpDto loginForOtpDto) {
		try {
			userService.sendOtpForPassword(loginForOtpDto.getLogin());
			return ResponseEntity.ok("label_sentOtpSuccessed");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/registration/resend-otp")
	public ResponseEntity<?> resendOtp(@RequestBody LoginForOtpDto loginForOtpDto) {
		try {
			userService.sendOtpForPassword(loginForOtpDto.getLogin());
			return ResponseEntity.ok("label_sentOtpSuccessed");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("password/recovery/confirm")
	public ResponseEntity<?> passwordRecoveryConfirm(@RequestBody NewPasswordConfirmBody newPasswordConfirmBody) {
		try {
			userService.newPasswordConfirm(newPasswordConfirmBody);
			return ResponseEntity.ok("Recovery Successed");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("registration/confirm")
	public ResponseEntity<?> passwordRegistrationConfirm(@RequestBody NewPasswordConfirmBody newPasswordConfirmBody) {
		try {
			userService.newPasswordConfirm(newPasswordConfirmBody);
			return ResponseEntity.ok("Recovery Successed");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getUserFromToken")
	public ResponseEntity<?> getUserFromToken(@RequestParam String token) {
		try {
			User user = userService.getUserFromToken(token);
			return ResponseEntity.ok(UserMapper.INSTANCE.mapUser(user));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/verifyTotp")
	public ResponseEntity<?> verifyTotp(@RequestBody VerificationTotpLogin data) throws ParseException {
		VerificationResponse verificationResponse = VerificationResponse.builder().username(data.getLogin()).tokenValid(
				Boolean.FALSE).message("TOTP is not Valid. Please try again.").build();

		// Validate the OTP
		if (userService.verifyTotp(data.getTotp(), data.getLogin())) {
			// GENERATE JWT
			verificationResponse = VerificationResponse.builder().username(data.getLogin()).tokenValid(Boolean.TRUE)
					.message("Token is valid").jwt(securityService.generateJwt(data.getLogin())).build();
		}

		return ResponseEntity.ok(verificationResponse);
//		if(verificationResponse.ge)
	}

	@GetMapping("/getConfigValueByCode")
	public String getConfigValueByCode(@RequestParam String code) {
		return zFinConfigService.getConfigValueByCode(code);
	}

}
