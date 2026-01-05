package com.abdul.email.adapter.in.web.controller;

import com.abdul.email.domain.common.model.BaseResponseMessageInfo;
import com.abdul.email.domain.email.model.ContactFormRequest;
import com.abdul.email.domain.email.model.SendBulkTextEmailInfo;
import com.abdul.email.domain.email.port.in.SendBulkEmailUseCase;
import com.abdul.email.domain.email.port.in.TemplateEmailUseCase;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final SendBulkEmailUseCase sendBulkEmailUseCase;
    private final TemplateEmailUseCase templateEmailUseCase;

    @PostMapping("/contact-form")
    public ResponseEntity<BaseResponseMessageInfo> sendContactFormEmail(
            @Valid @RequestBody ContactFormRequest request) throws MessagingException {
        log.info("Received contact form submission from: {}", request.getEmail());
        templateEmailUseCase.sendContactFormEmail(request);
        return ResponseEntity.ok(new BaseResponseMessageInfo("Email sent successfully!"));
    }

    @PostMapping("/bulk")
    public ResponseEntity<BaseResponseMessageInfo> sendBulkEmail(@Valid @RequestBody SendBulkTextEmailInfo sendBulkTextEmailInfo) {
        sendBulkEmailUseCase.execute(sendBulkTextEmailInfo);
        return ResponseEntity.ok(new BaseResponseMessageInfo("Email sent successfully!"));
    }

    @PostMapping("/bulk/schedule")
    public ResponseEntity<BaseResponseMessageInfo> scheduleBulkEmail(@Valid @RequestBody SendBulkTextEmailInfo sendBulkTextEmailInfo) {
        sendBulkEmailUseCase.schedule(sendBulkTextEmailInfo);
        return ResponseEntity.ok(new BaseResponseMessageInfo());
    }

}
