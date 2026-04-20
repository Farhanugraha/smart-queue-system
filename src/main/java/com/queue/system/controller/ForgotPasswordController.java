package com.queue.system.controller;


import com.queue.system.dto.request.ForgotPasswordRequest;
import com.queue.system.dto.request.ResetPasswordRequest;
import com.queue.system.dto.response.ApiResponse;
import com.queue.system.service.ForgotPasswordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService){
        this.forgotPasswordService = forgotPasswordService;
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Map<String, String >>> forgotPassword (
            @Valid @RequestBody ForgotPasswordRequest request) {

        try {
            String token = forgotPasswordService.generateResetToken(request.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Token reset password telah dibuat");
            response.put("token", token);

            return ResponseEntity.ok(ApiResponse.success(response));
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }

    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        try {
            forgotPasswordService.resetPassword(
                    request.getToken(),
                    request.getNewPassword(),
                    request.getConfirmPassword()
            );

            return ResponseEntity.ok(ApiResponse.success("Password berhasil direset! Silakan login dengan password baru."));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestParam String token) {
        boolean isValid = forgotPasswordService.validateToken(token);
        return ResponseEntity.ok(ApiResponse.success(isValid));
    }
}
