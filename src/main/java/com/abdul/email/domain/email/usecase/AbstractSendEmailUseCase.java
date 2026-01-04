package com.abdul.email.domain.email.usecase;

import com.abdul.email.domain.email.UnableToSendEmailException;
import com.abdul.email.domain.email.model.NumericOtpInfo;
import com.abdul.email.domain.email.port.in.OtpPublisher;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class AbstractSendEmailUseCase {

    private final OtpPublisher otpPublisher;

    public void execute(String toEmail) {
        try {
            otpPublisher.sendNumericOtpInfo(
                    new NumericOtpInfo(
                            toEmail,
                            sendMail(toEmail)
                    )
            );
        } catch (MessagingException e) {
            throw new UnableToSendEmailException(e.getMessage());
        }
    }

    abstract String sendMail(String toEmail) throws MessagingException;
}
