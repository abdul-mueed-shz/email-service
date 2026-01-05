package com.abdul.email.domain.email.usecase;

import com.abdul.email.adapter.out.persistence.repository.jpa.EmailJpaRepository;
import com.abdul.email.domain.email.model.EmailRequest;
import com.abdul.email.domain.email.port.in.AbstractInitEmailUseCase;
import com.abdul.email.domain.email.port.in.SendEmailUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("text")
@Slf4j
public class InitTextualEmailUseCase extends AbstractInitEmailUseCase {
    private final SendEmailUseCase sendEmailUseCase;

    public InitTextualEmailUseCase(
            SendEmailUseCase sendEmailUseCase,
            EmailJpaRepository emailJpaRepository) {
        super(emailJpaRepository);
        this.sendEmailUseCase = sendEmailUseCase;
    }

    @Override
    public void sendMail(EmailRequest request) {
        sendEmailUseCase.sendTextEmail(
                request.getToEmail(),
                request.getVariables().get("subject"),
                request.getVariables().get("body")
        );
    }

    @Override
    public boolean isInvalid(Map<String, String> variables) {
        return !variables.containsKey("subject") || !variables.containsKey("body");
    }
}
