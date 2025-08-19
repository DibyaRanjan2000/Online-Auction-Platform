package com.bluepal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//dto/AuthDtos.java
@Data
public class RegisterReq {
	@NotBlank
	String username;
	@NotBlank
	String password;
	@Email
	String email;
	String role;
}


