package com.abdul.email.domain.email.usecase;

import com.abdul.email.domain.email.model.TemplateEmailRequest;
import com.abdul.email.domain.email.port.in.SendEmailUseCase;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailUseCaseImpl implements SendEmailUseCase {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private static final String LOGO_PATH = "assets/StackWorld.png";
    private static final String LOGO_CONTENT_ID = "logo";

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async("threadPoolTaskExecutor")
    @Override
    public void sendTextEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Async("threadPoolTaskExecutor")
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

