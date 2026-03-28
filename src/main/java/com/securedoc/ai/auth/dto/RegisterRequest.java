package com.securedoc.ai.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "FirstName is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid Email address")
    private String email;


    @NotBlank(message = "Password is required")
    @Size(min = 8,message = "password must be at least 8 characters")
    @Size(max=100,message = "password must not exceed 100 characters")
    private String password;

}
