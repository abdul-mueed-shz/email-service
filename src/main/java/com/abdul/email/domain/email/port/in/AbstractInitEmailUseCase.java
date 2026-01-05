package com.abdul.email.domain.email.port.in;

import com.abdul.email.adapter.out.persistence.entity.Email;
import com.abdul.email.adapter.out.persistence.repository.jpa.EmailJpaRepository;
import com.abdul.email.domain.email.enums.EmailTypeEnum;
import com.abdul.email.domain.email.model.BulkEmailRequest;
import com.abdul.email.domain.email.model.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public abstract class AbstractInitEmailUseCase {
    private final EmailJpaRepository emailJpaRepository;

    public void execute(EmailRequest request) throws MessagingException {
        if (isInvalid(request.getVariables())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email request");
        }
        sendMail(request);
    }

    public void bulkExecute(BulkEmailRequest bulkEmailRequest) throws MessagingException {
        for (String email : bulkEmailRequest.targetEmails()) {
            execute(
                    EmailRequest.builder()
                            .toEmail(email)
                            .variables(bulkEmailRequest.variables())
                            .build()
            );
        }
    }

    @Transactional
    public void schedule(BulkEmailRequest bulkEmailRequest, EmailTypeEnum emailTypeEnum) throws MessagingException {
        List<Email> emails = new ArrayList<>();
        for (String targetEmail : bulkEmailRequest.targetEmails()) {
            if (isInvalid(bulkEmailRequest.variables())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email request");
            }
            Email email = new Email();
            email.setTargetEmail(targetEmail);
            email.setVariables(bulkEmailRequest.variables());
            email.setType(emailTypeEnum);
            emails.add(email);
        }
        emailJpaRepository.saveAll(emails);
    }

    public abstract void sendMail(EmailRequest request) throws MessagingException;

    public abstract boolean isInvalid(Map<String, String> variables) throws MessagingException;
}
