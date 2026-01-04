package com.abdul.email.domain.email.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SendBulkTextEmailInfo(
        @NotNull List<String> targetEmails,
        @NotNull String subject,
        @NotNull String body
) {
}
