package com.abdul.email.domain.email.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Request DTO for sending template-based emails.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateEmailRequest {

    @NotBlank(message = "Template name is required")
    private String templateName;

    @NotBlank(message = "Subject is required")
    @Size(max = 200, message = "Subject must not exceed 200 characters")
    private String subject;

    @NotEmpty(message = "At least one recipient is required")
    private List<String> recipients;

    /**
     * Template variables to be replaced in the template.
     * Keys should match the Thymeleaf variable names in the template.
     */
    private Map<String, Object> templateVariables;
}

