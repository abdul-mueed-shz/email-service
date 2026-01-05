package com.abdul.email.domain.email.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record BulkEmailRequest(
        @NotNull List<String> targetEmails,
        @NotNull Map<String, String> variables
) {
}
