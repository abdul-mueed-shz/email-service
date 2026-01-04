package com.abdul.email.domain.email.usecase;

import com.abdul.email.adapter.out.persistence.entity.Email;
import com.abdul.email.adapter.out.persistence.repository.jpa.EmailJpaRepository;
import com.abdul.email.domain.email.model.SendBulkTextEmailInfo;
import com.abdul.email.domain.email.port.in.SendBulkEmailUseCase;
import com.abdul.email.domain.email.port.in.SendTextualEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SendBulkEmailUseCaseImpl implements SendBulkEmailUseCase {

    private final SendTextualEmailUseCase sendTextualEmailUseCase;
    private final EmailJpaRepository emailJpaRepository;

    @Override
    public void execute(SendBulkTextEmailInfo sendBulkTextEmailInfo) {
        for (String email : sendBulkTextEmailInfo.targetEmails()) {
            sendTextualEmailUseCase.executeAsync(email, sendBulkTextEmailInfo.subject(), sendBulkTextEmailInfo.body());
        }
    }

    @Override
    public void schedule(SendBulkTextEmailInfo sendBulkTextEmailInfo) {
        emailJpaRepository.saveAll(sendBulkTextEmailInfo.targetEmails().stream().map(targetEmail -> {
            Email email = new Email();
            email.setTargetEmail(targetEmail);
            email.setSubject(sendBulkTextEmailInfo.subject());
            email.setBody(sendBulkTextEmailInfo.body());
            return email;
        }).toList());
    }
}