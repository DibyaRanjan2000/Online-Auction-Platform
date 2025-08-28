package com.bluepal.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	// 32 chars for HS256 (32 bytes = 256 bits)
	private static final String SECRET = "this_is_a_very_long_secret_key_1234567890";
	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

	private final long expiryMs = 24 * 60 * 60 * 1000L; // 24 hours

	public String generateToken(String username, String role) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + expiryMs);

		return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(now).setExpiration(exp)
				.signWith(KEY, SignatureAlgorithm.HS256).compact();
	}

	public Jws<Claims> parse(String token) {
		return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
	}
}
