package com.abdul.email.domain.auth.model;

import jakarta.validation.constraints.NotNull;

public record ClientCredentialsAuthRequest(
        @NotNull String clientId,
        @NotNull String clientSecret
) {
}
