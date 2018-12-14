package com.api.qerp.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.qerp.exception.BadRequestException;
import com.api.qerp.model.AuthProvider;
import com.api.qerp.model.User;
import com.api.qerp.payload.ApiResponse;
import com.api.qerp.payload.AuthResponse;
import com.api.qerp.payload.LoginRequest;
import com.api.qerp.payload.SignUpRequest;
import com.api.qerp.repository.UserRepository;
import com.api.qerp.security.TokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
				() -> new UsernameNotFoundException("User not found with email : " + loginRequest.getEmail()));

		String token = tokenProvider.createToken(authentication);
		user.setToken(token);
		userRepository.saveAndFlush(user);

		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("Email address already in use.");
		}

		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully@"));
	}

	@PutMapping("/user/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			User user = new User();
			user.setName(signUpRequest.getName());
			user.setEmail(signUpRequest.getEmail());
			user.setPassword(signUpRequest.getPassword());
			user.setProvider(AuthProvider.local);

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			User result = userRepository.saveAndFlush(user);

			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
					.buildAndExpand(result.getId()).toUri();

			return ResponseEntity.created(location).body(new ApiResponse(true, "User updated successfully@"));
		} else {
			throw new BadRequestException("Email doesn't exist.");

		}

	}

}
