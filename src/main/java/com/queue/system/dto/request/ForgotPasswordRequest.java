package com.queue.system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordRequest {

    @NotBlank(message = "Email tidak boleh kosong!")
    @Email(message = "Format email tidak valid!")
    private String email;

    public ForgotPasswordRequest(){}

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
