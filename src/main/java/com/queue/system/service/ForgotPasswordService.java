package com.queue.system.service;


import com.queue.system.entity.PasswordResetToken;
import com.queue.system.entity.User;
import com.queue.system.repository.PasswordResetTokenRepository;
import com.queue.system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;


    public ForgotPasswordService(UserRepository userRepository, PasswordResetTokenRepository tokenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String generateResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email tidak ditemukan"));


        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(expiryDate);
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        return  token;
    }

    @Transactional
    public void resetPassword(String token, String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Password dan konfirmasi password tidak sama!");
        }

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token tidak valid!"));

        if (resetToken.isUsed()) {
            throw new RuntimeException("Token sudah digunakan!");
        }

        if (resetToken.isExpired()) {
            throw new RuntimeException("Token sudah kadaluarsa! Silakan request ulang.");
        }

        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

    public boolean validateToken(String token) {
        return tokenRepository.findByToken(token)
                .map(resetToken -> !resetToken.isUsed() && !resetToken.isExpired())
                .orElse(false);
    }

}
