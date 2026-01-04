package com.abdul.email.domain.email.port.in;

public interface SendTextualEmailUseCase {

    void executeAsync(String toEmail, String subject, String body);

    void execute(String toEmail, String subject, String body);
}
