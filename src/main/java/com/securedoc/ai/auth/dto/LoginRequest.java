package com.securedoc.ai.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginRequest {

    @NotBlank(message = "email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

}
