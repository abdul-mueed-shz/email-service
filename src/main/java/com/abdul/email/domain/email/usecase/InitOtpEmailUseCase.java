package com.abdul.email.domain.email.usecase;

import com.abdul.email.adapter.out.persistence.repository.jpa.EmailJpaRepository;
import com.abdul.email.domain.email.model.EmailRequest;
import com.abdul.email.domain.email.model.NumericOtpInfo;
import com.abdul.email.domain.email.model.TemplateEmailRequest;
import com.abdul.email.domain.email.port.in.AbstractInitEmailUseCase;
import com.abdul.email.domain.email.port.in.OtpPublisher;
import com.abdul.email.domain.email.port.in.SendEmailUseCase;
import com.abdul.email.domain.email.utils.OtpGenerator;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("otp")
public class InitOtpEmailUseCase extends AbstractInitEmailUseCase {

    private final OtpPublisher otpPublisher;
    private final SendEmailUseCase sendEmailUseCase;

    public InitOtpEmailUseCase(
            OtpPublisher otpPublisher,
            SendEmailUseCase sendEmailUseCase,
            EmailJpaRepository emailJpaRepository) {
        super(emailJpaRepository);
        this.sendEmailUseCase = sendEmailUseCase;
        this.otpPublisher = otpPublisher;
    }

    @Override
    public void sendMail(EmailRequest request) throws MessagingException {
        String otp = OtpGenerator.generateSixDigitNumericOTP();
        Map<String, String> context = new HashMap<>();
        context.put("otp", otp);
        sendEmailUseCase.sendTemplateEmail(TemplateEmailRequest.builder()
                .templateName("otp-mail")
                .subject("Your OTP Code")
                .recipients(List.of(request.getToEmail()))
                .templateVariables(context)
                .build());
        otpPublisher.sendNumericOtpInfo(
                new NumericOtpInfo(
                        request.getToEmail(),
                        otp
                )
        );
    }

    public boolean isInvalid(Map<String, String> variables) {
        return Boolean.TRUE;
    }
}
