package com.abdul.email.domain.auth.model;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request DTO for Cognito OAuth2 token endpoint.
 * Uses form-urlencoded format.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CognitoTokenRequest {

    @FormProperty("grant_type")
    @Builder.Default
    private String grantType = "client_credentials";

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("client_secret")
    private String clientSecret;

    @FormProperty("scope")
    private String scope;
}

