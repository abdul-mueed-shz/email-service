package com.abdul.email.adapter.in.web.controller;

import com.abdul.email.domain.auth.model.ClientCredentialsAuthRequest;
import com.abdul.email.domain.auth.model.TokenResponse;
import com.abdul.email.domain.auth.port.in.OAuth2TokenUseCase;
import com.abdul.email.domain.common.model.ResponseMessageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final OAuth2TokenUseCase oAuth2TokenUseCase;

    @PostMapping("/client-credentials")
    public ResponseEntity<ResponseMessageInfo<TokenResponse>> authenticateClient
            (@Valid @RequestBody ClientCredentialsAuthRequest clientCredentialsAuthRequest) {
        ResponseMessageInfo<TokenResponse> responseMessageInfo =
                new ResponseMessageInfo<>(oAuth2TokenUseCase.getTokenWithCredentials(
                        clientCredentialsAuthRequest.clientId(),
                        clientCredentialsAuthRequest.clientSecret(),
                        null
                ));
        return ResponseEntity.ok(responseMessageInfo);
    }
}
