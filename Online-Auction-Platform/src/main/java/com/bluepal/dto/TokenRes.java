package com.bluepal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRes {
    private String username;
    private String role;
    private String token;
}
