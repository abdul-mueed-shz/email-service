package com.abdul.email.domain.email.port.in;

import com.abdul.email.domain.email.model.SendBulkTextEmailInfo;


public interface SendBulkEmailUseCase {
    void execute(SendBulkTextEmailInfo sendBulkTextEmailInfo);

    void schedule(SendBulkTextEmailInfo sendBulkTextEmailInfo);
}
