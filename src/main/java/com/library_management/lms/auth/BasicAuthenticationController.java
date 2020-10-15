package com.library_management.lms.auth;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library_management.lms.model.User;
import com.library_management.lms.repository.UserRepository;

//Controller
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BasicAuthenticationController {
	@Autowired
	UserRepository userRepository;

	@GetMapping(path = "/basicauth")
	public User helloWorldBean(Principal principal) {
		// throw new RuntimeException("Some Error has Happened! Contact Support at
		// ***-***");
		String email = principal.getName();
		Optional<User> user = userRepository.findByEmail(email);
		return user.orElseThrow(() -> new UsernameNotFoundException("Not Found!"));
	}
}
