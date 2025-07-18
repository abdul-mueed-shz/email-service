package com.abdul.email.domain.email.usecase;

import com.abdul.email.domain.email.port.in.SendCustomTextualEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendCustomTextualEmailUseCaseImpl implements SendCustomTextualEmailUseCase {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    public String fromEmail;

    @Override
    public void execute(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
