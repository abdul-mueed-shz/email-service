package com.abdul.email.domain.email.usecase;

import com.abdul.email.config.AppProperties;
import com.abdul.email.domain.email.model.ContactFormRequest;
import com.abdul.email.domain.email.model.TemplateEmailRequest;
import com.abdul.email.domain.email.port.in.TemplateEmailUseCase;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateEmailUseCaseImpl implements TemplateEmailUseCase {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private static final String LOGO_PATH = "assets/StackWorld.png";
    private static final String LOGO_CONTENT_ID = "logo";

    private final AppProperties appProperties;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendContactFormEmail(ContactFormRequest request) throws MessagingException {
        log.info("Sending contact form email from: {}", request.getEmail());

        Context context = new Context();
        context.setVariable("name", request.getName());
        context.setVariable("email", request.getEmail());
        context.setVariable("subject", request.getSubject());
        context.setVariable("message", request.getMessage());

        String htmlContent = templateEngine.process("contact-form-mail", context);
        String subject = "New Contact Form: " + request.getSubject();

        List<String> validAdmins = appProperties.getEmail().getAdminRecipients().stream()
                .filter(email -> email != null && !email.isBlank())
                .toList();

        for (String adminEmail : validAdmins) {
            sendHtmlEmail(adminEmail, subject, htmlContent);
        }

        log.info("Contact form email sent successfully to {} admin(s)", validAdmins.size());
    }

    public void sendTemplateEmail(TemplateEmailRequest request) throws MessagingException {
        log.info("Sending template email: {} to {} recipient(s)",
                request.getTemplateName(), request.getRecipients().size());

        Context context = new Context();
        if (request.getTemplateVariables() != null) {
            request.getTemplateVariables().forEach(context::setVariable);
        }

        String htmlContent = templateEngine.process(request.getTemplateName(), context);

        for (String recipient : request.getRecipients()) {
            sendHtmlEmail(recipient, request.getSubject(), htmlContent);
        }

        log.info("Template email sent successfully");
    }

    public List<String> sendBulkTemplateEmail(TemplateEmailRequest request) {
        log.info("Sending bulk template email: {} to {} recipients",
                request.getTemplateName(), request.getRecipients().size());

        List<String> failedRecipients = new ArrayList<>();

        Context context = new Context();
        if (request.getTemplateVariables() != null) {
            request.getTemplateVariables().forEach(context::setVariable);
        }

        String htmlContent = templateEngine.process(request.getTemplateName(), context);

        for (String recipient : request.getRecipients()) {
            try {
                sendHtmlEmail(recipient, request.getSubject(), htmlContent);
                log.debug("Email sent successfully to: {}", recipient);
            } catch (MessagingException e) {
                log.warn("Failed to send email to: {}", recipient);
                failedRecipients.add(recipient);
            }
        }

        log.info("Bulk email completed. Success: {}, Failed: {}",
                request.getRecipients().size() - failedRecipients.size(),
                failedRecipients.size());

        return failedRecipients;
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        ClassPathResource logoResource = new ClassPathResource(LOGO_PATH);
        if (logoResource.exists()) {
            helper.addInline(LOGO_CONTENT_ID, logoResource);
        }

        mailSender.send(message);
    }
}

