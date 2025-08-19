package com.bluepal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bluepal.service.UserService;

import lombok.RequiredArgsConstructor;

//security/SecurityConfig.java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigs {
	private final JwtAuthFilter jwtAuthFilter;
	private final UserService userService;

//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(
//						auth -> auth.requestMatchers("/auth/**", "/ws/**").permitAll().anyRequest().authenticated())
//				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//		return http.build();
//	}
	@Bean
	public PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authprovider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(pwdEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();

	}

//	@Bean
//	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
//		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/register", "/api/login").permitAll()
//				.requestMatchers("/api/**").authenticated().and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authenticationProvider(authprovider()) // Ensure
//																												// defined
//				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Ensure `filter` is valid
//				.build();
//	}
	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(
//						auth -> auth.requestMatchers("/auth/**", "/ws/**").permitAll().anyRequest().authenticated())
//				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//		return http.build();
//	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(
	                "/auth/**",            // your existing public auth APIs
	                "/ws/**",              // if using WebSockets
	                "/v3/api-docs/**",     // ✅ OpenAPI docs
	                "/swagger-ui/**",      // ✅ Swagger UI resources
	                "/swagger-ui.html"
	               //  ✅ Swagger UI main page
	            ).permitAll()
	            .anyRequest().authenticated()
	        )
	        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}


}
