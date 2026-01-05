package com.abdul.email.domain.email.port.in;

import com.abdul.email.domain.email.model.ContactFormRequest;
import jakarta.mail.MessagingException;

public interface TemplateEmailUseCase {

    void sendContactFormEmail(ContactFormRequest request) throws MessagingException;
}

