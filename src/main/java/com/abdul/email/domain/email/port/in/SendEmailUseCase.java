package com.abdul.email.domain.email.port.in;

import com.abdul.email.domain.email.model.TemplateEmailRequest;
import jakarta.mail.MessagingException;

public interface SendEmailUseCase {
    void sendTemplateEmail(TemplateEmailRequest request) throws MessagingException;

    void sendTextEmail(String toEmail, String subject, String body);
}

