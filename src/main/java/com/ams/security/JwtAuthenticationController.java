package com.ams.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ams.Utility.EncryptionUtil;
import com.ams.api.admin.service.UserService;
import com.ams.exception.ApplicationException;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(tags = { "Authentication Controller" })
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtTokenUtil jwtTokenUtil;
	//private final CaptchaService captchaService;

	@Value("${secret-key}")
	private char[] secretKey ;

	@Value("${salt}")
	private String salt;
	
	@PostMapping(value = "/external/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest loginRequest)
			throws Exception {

//		boolean captchaVerified = captchaService.verify(loginRequest.getRecaptchaResponse());
//
//		if (!captchaVerified) {
//			throw new InvalidCaptchaException("Invalid Captcha");
//		}

		Authentication authentication = this.authenticate(loginRequest);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtTokenUtil.generateToken(loginRequest.getUserId());
		User userDetails = (User) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		userService.updateToken(userDetails.getUsername(), jwtTokenUtil.getExpirationDateFromToken(jwt),jwt);

		//   userService.insertLoginHistory(userDetails.getUsername());
		jwt =  EncryptionUtil.encrypt(jwt.toCharArray(), this.secretKey, this.salt);

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles, userService.isSecurityQuestionUpdated(userDetails.getUsername())));
	}

	@PostMapping(value = "/maps/logout")
	public ResponseEntity<Boolean> logout(Authentication authentication) throws Exception {

		if (authentication == null)
			return ResponseEntity.ok(Boolean.FALSE);

		if (UsernamePasswordAuthenticationToken.class.isInstance(authentication)) {
			jwtTokenUtil
					.invalidateToken((String) ((UsernamePasswordAuthenticationToken) authentication).getCredentials());
			String userId = ((User) authentication.getPrincipal()).getUsername();
			userService.clearToken(userId);
			userService.removeUserMenuCache(userId);
		}
		authentication.setAuthenticated(false);
		SecurityContextHolder.clearContext();
		
		return ResponseEntity.ok(Boolean.TRUE);
	}
	
	private Authentication authenticate(JwtRequest jwtRequest) throws Exception {

		try {			
			return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtRequest.getUserId(), jwtRequest.getPassword()));
		} catch (DisabledException e) {
			throw new ApplicationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			
			throw new BadCredentialsException("INVALID_CREDENTIALS!", e);			
		}
	}
}
