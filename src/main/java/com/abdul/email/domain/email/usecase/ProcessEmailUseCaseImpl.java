package com.abdul.email.domain.email.usecase;

import com.abdul.email.adapter.out.persistence.entity.Email;
import com.abdul.email.adapter.out.persistence.repository.jpa.EmailJpaRepository;
import com.abdul.email.domain.email.enums.EmailStatusEnum;
import com.abdul.email.domain.email.model.EmailRequest;
import com.abdul.email.domain.email.port.in.AbstractInitEmailUseCase;
import com.abdul.email.domain.email.port.in.ProcessEmailUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessEmailUseCaseImpl implements ProcessEmailUseCase {
    private final EmailJpaRepository emailJpaRepository;
    private final ApplicationContext applicationContext;

    @Override
    public void execute(Long id) {
        Optional<Email> emailOptional = emailJpaRepository.findById(id);
        if (emailOptional.isEmpty()) {
            return;
        }
        Email email = emailOptional.get();
        if (email.getStatus() != EmailStatusEnum.PENDING) {
            return;
        }
        if (email.getRetryAttempt() >= 3) {
            log.info("Failed to send email to recipient {}", email.getTargetEmail());
            email.setStatus(EmailStatusEnum.FAILED);
            emailJpaRepository.save(email);
            return;
        }
        try {
            AbstractInitEmailUseCase abstractInitEmailUseCase =
                    (AbstractInitEmailUseCase) applicationContext.getBean(email.getType().getType());
            abstractInitEmailUseCase.execute(
                    EmailRequest.builder()
                            .toEmail(email.getTargetEmail())
                            .variables(email.getVariables())
                            .build()
            );
            email.setStatus(EmailStatusEnum.SENT);
            emailJpaRepository.save(email);
            log.info("Email sent to recipient {}", email.getTargetEmail());
        } catch (Exception e) {
            log.error("Error sending email to recipient {} : {}, Will retry later",
                    email.getTargetEmail(), e.getMessage());
            email.setRetryAttempt(email.getRetryAttempt() + 1);
            emailJpaRepository.save(email);
        }
    }
}
