package com.abdul.email.domain.email.port.in;

import com.abdul.email.domain.email.enums.EmailStatusEnum;

import java.util.List;

public interface FetchEmailUseCase {
    List<Long> findByStatusAndSufficientRetries(EmailStatusEnum emailStatusEnum);
}
