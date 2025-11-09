package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.*;
import com.example.neu.entity.VerificationCode;
import com.example.neu.exception.UsernameAlreadyExistsException;
import com.example.neu.security.JwtUtil;
import com.example.neu.entity.User;
import com.example.neu.repository.UserRepository;
import com.example.neu.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    public static final String SUCCESS = "SUCCESS";
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;


    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        // ✅ Xác thực username & password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        // ✅ Lấy thông tin user từ UserDetails
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        // ✅ Tìm user trong DB để lấy thêm id, email, role
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();

        APIResponse<AuthResponse> apiResponse = APIResponse.<AuthResponse>builder()
                .status("SUCCESS")
                .result(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/send-verification-code")
    public ResponseEntity<APIResponse<String>> sendVerificationCode(@RequestBody SendVerificationCodeRequest request) {
        if (userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new UsernameAlreadyExistsException(request.getEmail());
        }

        verificationService.generateAndSendVerificationCode(
                request.getEmail(),
                VerificationCode.CodeType.REGISTRATION
        );

        APIResponse<String> response = APIResponse.<String>builder()
                .status("SUCCESS")
                .result("Verification code sent to email")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new UsernameAlreadyExistsException("Email already exists");
        }


        // Verify the code
        boolean isValid = verificationService.verifyCode(
                registerRequest.getEmail(),
                registerRequest.getVerificationCode(),
                VerificationCode.CodeType.REGISTRATION
        );

        if (!isValid) {
            APIResponse<String> response = APIResponse.<String>builder()
                    .status("ERROR")
                    .result(null)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(User.Role.USER);
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);

        APIResponse<String> apiResponse = APIResponse.<String>builder()
                .status("SUCCESS")
                .result("User registered successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/forgot-password/send-code")
    public ResponseEntity<APIResponse<String>> sendPasswordResetCode(@RequestBody ForgotPasswordRequest request) {
        System.out.println(request.getEmail());
        System.out.println(userRepository.findByEmail(request.getEmail()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        verificationService.generateAndSendVerificationCode(
                request.getEmail(),
                VerificationCode.CodeType.PASSWORD_RESET
        );

        APIResponse<String> response = APIResponse.<String>builder()
                .status("SUCCESS")
                .result("Password reset code sent to email")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password/reset")
    public ResponseEntity<APIResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean isValid = verificationService.verifyCode(
                request.getEmail(),
                request.getVerificationCode(),
                VerificationCode.CodeType.PASSWORD_RESET
        );

        if (!isValid) {
            APIResponse<String> response = APIResponse.<String>builder()
                    .status("ERROR")
                    .result("Invalid or expired verification code")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        APIResponse<String> response = APIResponse.<String>builder()
                .status("SUCCESS")
                .result("Password reset successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}