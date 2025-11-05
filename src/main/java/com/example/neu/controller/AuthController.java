package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.exception.UsernameAlreadyExistsException;
import com.example.neu.security.JwtUtil;
import com.example.neu.dto.AuthRequest;
import com.example.neu.dto.AuthResponse;
import com.example.neu.dto.RegisterRequest;
import com.example.neu.entity.User;
import com.example.neu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse<User>> register(@RequestBody RegisterRequest registerRequest) {
        final String SUCCESS = "SUCCESS";

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {

            throw new UsernameAlreadyExistsException(registerRequest.getUsername());
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(User.Role.USER); // default role
        userRepository.save(user);

        APIResponse<User> apiResponse = APIResponse.<User>builder()
                .status(SUCCESS)
                .result(user)
                .build();

        return ResponseEntity.ok(apiResponse);
    }



}