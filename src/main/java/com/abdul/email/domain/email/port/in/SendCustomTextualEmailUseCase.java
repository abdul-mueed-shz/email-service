package com.abdul.email.domain.email.port.in;

public interface SendCustomTextualEmailUseCase {

    void execute(String toEmail, String subject, String body);
}
