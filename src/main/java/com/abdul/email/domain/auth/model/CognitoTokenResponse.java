package com.abdul.email.domain.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Response DTO from Cognito OAuth2 token endpoint.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CognitoTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("scope")
    private String scope;
}

