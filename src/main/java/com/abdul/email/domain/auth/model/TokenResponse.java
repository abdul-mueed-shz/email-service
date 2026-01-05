package com.abdul.email.domain.auth.model;

/**
 * Token response DTO
 */
@lombok.Builder
@lombok.Getter
@lombok.AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private String scope;
}