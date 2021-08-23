package com.example.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.exception.UserNotFoundException;
import com.example.login.model.User;
import com.example.login.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
	User signUp(@RequestBody User newUser) {
		return this.userRepository.save(newUser);
	}

	@GetMapping("/users")
	List<User> findAll() {
		return this.userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	User findUser(@PathVariable int id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
	}
}
