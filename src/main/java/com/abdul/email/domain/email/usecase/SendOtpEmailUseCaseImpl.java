package com.abdul.email.domain.email.usecase;

import com.abdul.email.adapter.out.messaging.OtpProducer;
import com.abdul.email.domain.email.utils.OtpGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service("otp")
public class SendOtpEmailUseCaseImpl extends AbstractSendEmailUseCase {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public SendOtpEmailUseCaseImpl(
            OtpProducer otpProducer,
            JavaMailSender mailSender,
            TemplateEngine templateEngine) {
        super(otpProducer);
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public String sendMail(String toEmail) throws MessagingException {
        String otp = OtpGenerator.generateSixDigitNumericOTP();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Your OTP Code");

        Context context = new Context();
        context.setVariable("otp", otp);

        String htmlContent = templateEngine.process("otp-mail", context);
        helper.setText(htmlContent, true);
        mailSender.send(message);
        return otp;
    }
}
