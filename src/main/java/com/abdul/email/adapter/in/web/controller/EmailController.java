package com.abdul.email.adapter.in.web.controller;

import com.abdul.email.domain.common.model.ResponseMessageInfo;
import com.abdul.email.domain.email.model.SendBulkTextEmailInfo;
import com.abdul.email.domain.email.port.in.SendBulkEmailUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class EmailController {

    private final SendBulkEmailUseCase sendBulkEmailUseCase;

    @PostMapping("/bulk")
    public ResponseEntity<ResponseMessageInfo> sendBulkEmail(@Valid @RequestBody SendBulkTextEmailInfo sendBulkTextEmailInfo) {
        sendBulkEmailUseCase.execute(sendBulkTextEmailInfo);
        return ResponseEntity.ok(new ResponseMessageInfo());
    }

    @PostMapping("/bulk/schedule")
    public ResponseEntity<ResponseMessageInfo> scheduleBulkEmail(@Valid @RequestBody SendBulkTextEmailInfo sendBulkTextEmailInfo) {
        sendBulkEmailUseCase.schedule(sendBulkTextEmailInfo);
        return ResponseEntity.ok(new ResponseMessageInfo());
    }

}
