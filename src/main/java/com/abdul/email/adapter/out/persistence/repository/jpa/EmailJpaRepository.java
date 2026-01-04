package com.abdul.email.adapter.out.persistence.repository.jpa;

import com.abdul.email.adapter.out.persistence.entity.Email;
import com.abdul.email.domain.email.enums.EmailStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailJpaRepository extends JpaRepository<Email, Long> {
    List<Email> findByStatusAndRetryAttemptLessThanEqual(EmailStatusEnum status, Long retryAttempt);
}
