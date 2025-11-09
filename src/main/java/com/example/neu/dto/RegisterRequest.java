package com.example.neu.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email; // Email is now the unique identifier for registration
    private String password;
    private String verificationCode;
}