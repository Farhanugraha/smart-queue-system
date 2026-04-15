package com.queue.system.service;


import com.queue.system.dto.request.LoginRequest;
import com.queue.system.dto.request.RegisterRequest;
import com.queue.system.dto.response.AuthResponse;
import com.queue.system.entity.Role;
import com.queue.system.entity.User;
import com.queue.system.repository.UserRepository;
import com.queue.system.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email sudah terdaftar!");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()){
            throw new RuntimeException("Nomor telepon sudah terdaftar!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail());

        return new AuthResponse(token,
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole() );

    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new RuntimeException("Email atau password salah!"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Email atau password salah!");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
