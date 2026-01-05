package com.abdul.email.domain.auth.port.in;

import com.abdul.email.domain.auth.model.TokenResponse;

public interface OAuth2TokenUseCase {
    TokenResponse getTokenWithCredentials(String clientId, String clientSecret, String scope);
}
