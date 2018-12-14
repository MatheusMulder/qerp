package com.api.qerp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.qerp.exception.ResourceNotFoundException;
import com.api.qerp.model.User;
import com.api.qerp.repository.UserRepository;
import com.api.qerp.security.CurrentUser;
import com.api.qerp.security.UserPrincipal;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}

	@GetMapping("/user/list/all")
	public ResponseEntity<?> listAllUsers() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty() == false) {
			return new ResponseEntity<Object>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		}
	}

}
