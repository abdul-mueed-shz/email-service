package com.abdul.email.domain.auth.usecase;

import com.abdul.email.adapter.out.cognito.CognitoFeignClient;
import com.abdul.email.domain.auth.model.CognitoTokenRequest;
import com.abdul.email.domain.auth.model.CognitoTokenResponse;
import com.abdul.email.domain.auth.model.TokenResponse;
import com.abdul.email.domain.auth.port.in.OAuth2TokenUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2TokenUseCaseImpl implements OAuth2TokenUseCase {

    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final CognitoFeignClient cognitoFeignClient;

    @Value("${spring.security.oauth2.client.registration.cognito.scope}")
    private String defaultScope;

    private static final String CLIENT_REGISTRATION_ID = "cognito";
    private static final String PRINCIPAL_NAME = "email-service";

    /**
     * Fetches an access token from Cognito using the provided client credentials.
     * Acts as a wrapper over Cognito's token endpoint.
     *
     * @param clientId     the OAuth2 client ID
     * @param clientSecret the OAuth2 client secret
     * @param scope        the requested scope (optional, uses default if null)
     * @return TokenResponse containing access token and metadata
     */
    public TokenResponse getTokenWithCredentials(String clientId, String clientSecret, String scope) {
        CognitoTokenRequest request = CognitoTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(scope != null ? scope : defaultScope)
                .build();

        CognitoTokenResponse response = cognitoFeignClient.getToken(request);

        return TokenResponse.builder()
                .accessToken(response.getAccessToken())
                .tokenType(response.getTokenType())
                .expiresIn(response.getExpiresIn())
                .scope(response.getScope())
                .build();
    }


    public String getAccessToken() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(CLIENT_REGISTRATION_ID)
                .principal(PRINCIPAL_NAME)
                .build();

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient == null) {
            log.error("Failed to obtain OAuth2 authorized client for registration: {}", CLIENT_REGISTRATION_ID);
            throw new IllegalStateException("Failed to obtain OAuth2 token - authorized client is null");
        }

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

        if (accessToken == null) {
            log.error("Access token is null for client registration: {}", CLIENT_REGISTRATION_ID);
            throw new IllegalStateException("Failed to obtain OAuth2 token - access token is null");
        }

        log.debug("Successfully obtained access token, expires at: {}", accessToken.getExpiresAt());
        return accessToken.getTokenValue();
    }

    public OAuth2AccessToken getAccessTokenObject() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(CLIENT_REGISTRATION_ID)
                .principal(PRINCIPAL_NAME)
                .build();

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

        return Objects.requireNonNull(authorizedClient, "Authorized client cannot be null")
                .getAccessToken();
    }

    public String getAuthorizationHeader() {
        return "Bearer " + getAccessToken();
    }

}

