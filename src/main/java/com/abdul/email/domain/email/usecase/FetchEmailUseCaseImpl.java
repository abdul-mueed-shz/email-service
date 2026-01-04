package com.abdul.email.domain.email.usecase;

import com.abdul.email.adapter.out.persistence.entity.Email;
import com.abdul.email.adapter.out.persistence.repository.jpa.EmailJpaRepository;
import com.abdul.email.domain.email.enums.EmailStatusEnum;
import com.abdul.email.domain.email.port.in.FetchEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchEmailUseCaseImpl implements FetchEmailUseCase {
    private final EmailJpaRepository emailJpaRepository;

    @Override
    public List<Long> findByStatusAndSufficientRetries(EmailStatusEnum emailStatusEnum) {
        return emailJpaRepository.findByStatusAndRetryAttemptLessThanEqual(emailStatusEnum, 5L).stream()
                .map(Email::getId)
                .toList();
    }
}
