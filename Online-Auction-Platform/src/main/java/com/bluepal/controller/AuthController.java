package com.bluepal.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bluepal.dto.LoginReq;
import com.bluepal.dto.RegisterReq;
import com.bluepal.dto.TokenRes;
import com.bluepal.entity.User;
import com.bluepal.repo.UserRepository;
import com.bluepal.security.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//controller/AuthController.java
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
	private final UserRepository users;
	private final PasswordEncoder encoder;
	private final JwtUtil jwt;

//	@PostMapping("/register")
//	public ResponseEntity<?> register(@Valid @RequestBody RegisterReq req) {
//		if (users.findByUsername(req.getUsername()).isPresent())
//			return ResponseEntity.status(409).build();
//		User u = User.builder().username(req.getUsername()).password(encoder.encode(req.getPassword()))
//				.email(req.getEmail()).role(Optional.ofNullable(req.getRole()).orElse("BUYER")).build();
//		users.save(u);
//		return ResponseEntity.ok().build();
//	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterReq req) {
		if (users.findByUsername(req.getUsername()).isPresent()) {
			return ResponseEntity.status(409).body("Username already exists");
		}

		User u = User.builder().username(req.getUsername()).password(encoder.encode(req.getPassword()))
				.email(req.getEmail()).role(Optional.ofNullable(req.getRole()).orElse("BUYER")).build();

		users.save(u);

		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/login")
	public TokenRes login(@Valid @RequestBody LoginReq req) {
		var u = users.findByUsername(req.getUsername())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

		if (!encoder.matches(req.getPassword(), u.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		String token = jwt.generateToken(u.getUsername(), u.getRole());

		return new TokenRes(u.getUsername(), u.getRole(), token);
	}

}
