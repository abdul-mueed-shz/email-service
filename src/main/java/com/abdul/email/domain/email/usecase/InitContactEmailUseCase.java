package com.abdul.email.domain.email.usecase;

import com.abdul.email.adapter.out.persistence.repository.jpa.EmailJpaRepository;
import com.abdul.email.config.AppProperties;
import com.abdul.email.domain.email.model.EmailRequest;
import com.abdul.email.domain.email.model.TemplateEmailRequest;
import com.abdul.email.domain.email.port.in.AbstractInitEmailUseCase;
import com.abdul.email.domain.email.port.in.SendEmailUseCase;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("contact")
@Slf4j
public class InitContactEmailUseCase extends AbstractInitEmailUseCase {
    private final SendEmailUseCase sendEmailUseCase;
    private final AppProperties appProperties;

    public InitContactEmailUseCase(
            SendEmailUseCase sendEmailUseCase,
            EmailJpaRepository emailJpaRepository,
            AppProperties appProperties) {
        super(emailJpaRepository);
        this.sendEmailUseCase = sendEmailUseCase;
        this.appProperties = appProperties;
    }

    @Override
    public void sendMail(EmailRequest request) throws MessagingException {
        log.info("Sending contact form email from: {}", request.getVariables().get("email"));
        String subject = "New Contact Form: " + request.getVariables().get("subject");
        List<String> validAdmins = appProperties.getEmail().getAdminRecipients().stream()
                .filter(email -> email != null && !email.isBlank())
                .toList();
        sendEmailUseCase.sendTemplateEmail(TemplateEmailRequest.builder()
                .recipients(validAdmins)
                .subject(subject)
                .templateName("contact-form-mail")
                .templateVariables(request.getVariables())
                .build());
        log.info("Contact form email sent successfully to {} admin(s)", validAdmins.size());
    }

    @Override
    public boolean isInvalid(Map<String, String> variables) {
        return !variables.containsKey("name")
                || !variables.containsKey("email")
                || !variables.containsKey("subject")
                || !variables.containsKey("message");
    }
}
