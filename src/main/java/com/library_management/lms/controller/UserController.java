package com.library_management.lms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.library_management.lms.exception.ResourceNotFoundException;
import com.library_management.lms.model.User;
import com.library_management.lms.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// get all users
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// create users
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		User encodedPasswordUser = new User();
		encodedPasswordUser.setPassword(passwordEncoder.encode(user.getPassword()));
		encodedPasswordUser.setEmail(user.getEmail());
		encodedPasswordUser.setName(user.getName());
		encodedPasswordUser.setRoles("USER");
		encodedPasswordUser.setBookId1(-1);
		encodedPasswordUser.setBookId2(-1);
		encodedPasswordUser.setBookId3(-1);
		return userRepository.save(encodedPasswordUser);
	}

	// get users by id
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + id));
		return ResponseEntity.ok(user);
	}

	// update user
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + id));

		user.setName(userDetails.getName());
		user.setBookId1(userDetails.getBookId1());
		user.setBookId2(userDetails.getBookId2());
		user.setBookId3(userDetails.getBookId3());
		user.setEmail(userDetails.getEmail());

		if (userDetails.getPassword() != "")
			user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

		User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	// delete user
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + id));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
