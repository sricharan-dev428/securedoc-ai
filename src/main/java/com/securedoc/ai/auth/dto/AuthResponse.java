package com.securedoc.ai.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AuthResponse {
    private String token;
    private String email;
    private String role;
    private String firstName;


}
