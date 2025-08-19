package com.bluepal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class LoginReq {
	@NotBlank
	String username;
	@NotBlank
	String password;
}
