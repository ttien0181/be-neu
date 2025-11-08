package com.example.neu.service;

import com.example.neu.entity.VerificationCode;
import com.example.neu.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private static final int CODE_EXPIRY_MINUTES = 10;
    private static final int CODE_LENGTH = 6;

    public String generateAndSendVerificationCode(String email, VerificationCode.CodeType type) {
        String code = generateRandomCode();

        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .type(type)
                .expiryTime(LocalDateTime.now().plusMinutes(CODE_EXPIRY_MINUTES))
                .used(false)
                .build();

        verificationCodeRepository.save(verificationCode);

        if (type == VerificationCode.CodeType.REGISTRATION) {
            emailService.sendVerificationCode(email, code);
        } else {
            emailService.sendPasswordResetCode(email, code);
        }

        return code;
    }

    @Transactional
    public boolean verifyCode(String email, String code, VerificationCode.CodeType type) {
        return verificationCodeRepository.findByEmailAndCodeAndType(email, code, type)
                .map(verificationCode -> {
                    if (verificationCode.isUsed() || verificationCode.isExpired()) {
                        return false;
                    }
                    verificationCode.setUsed(true);
                    verificationCodeRepository.save(verificationCode);
                    return true;
                })
                .orElse(false);
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}