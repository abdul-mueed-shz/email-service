package com.abdul.email.domain.email.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Request DTO for contact form email submissions.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private String toEmail;
    private Map<String, String> variables;
}

