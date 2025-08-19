package com.bluepal.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bluepal.repo.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwt;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {

		String header = req.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);

			try {
				var claims = jwt.parse(token).getBody();

				String username = claims.getSubject();
				String role = claims.get("role", String.class);

				var auth = new UsernamePasswordAuthenticationToken(username, null,
						List.of(new SimpleGrantedAuthority("ROLE_" + role)));

				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				System.out.println("JWT validation failed: " + e.getMessage());
			}
		}

		chain.doFilter(req, res);
	}
}
