package com.abdul.email.adapter.in.web.controller;

import com.abdul.email.domain.common.model.BaseResponseMessageInfo;
import com.abdul.email.domain.email.enums.EmailTypeEnum;
import com.abdul.email.domain.email.model.BulkEmailRequest;
import com.abdul.email.domain.email.model.EmailRequest;
import com.abdul.email.domain.email.port.in.AbstractInitEmailUseCase;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final ApplicationContext applicationContext;

    @PostMapping("/{type}")
    public ResponseEntity<BaseResponseMessageInfo> sendContactFormEmail(
            @PathVariable String type,
            @Valid @RequestBody EmailRequest emailRequest) throws MessagingException {
        validateType(type);
        AbstractInitEmailUseCase sendEmailUseCase =
                (AbstractInitEmailUseCase) applicationContext.getBean(type);
        log.info("Received contact form submission from: {}", emailRequest.getVariables().get("email"));
        sendEmailUseCase.execute(emailRequest);
        return ResponseEntity.ok(new BaseResponseMessageInfo("Email sent successfully!"));
    }

    @PostMapping("/{type}/bulk")
    public ResponseEntity<BaseResponseMessageInfo> sendBulkEmail(
            @PathVariable String type,
            @Valid @RequestBody BulkEmailRequest bulkEmailRequest) throws MessagingException {
        validateType(type);
        AbstractInitEmailUseCase sendEmailUseCase =
                (AbstractInitEmailUseCase) applicationContext.getBean(type);
        sendEmailUseCase.bulkExecute(bulkEmailRequest);
        return ResponseEntity.ok(new BaseResponseMessageInfo("Email sent successfully!"));
    }

    @PostMapping("/{type}/bulk/schedule")
    public ResponseEntity<BaseResponseMessageInfo> scheduleBulkEmail(
            @PathVariable String type,
            @Valid @RequestBody BulkEmailRequest bulkEmailRequest) throws MessagingException {
        validateType(type);
        AbstractInitEmailUseCase sendEmailUseCase =
                (AbstractInitEmailUseCase) applicationContext.getBean(type);
        sendEmailUseCase.schedule(bulkEmailRequest, EmailTypeEnum.fromString(type));
        return ResponseEntity.ok(new BaseResponseMessageInfo());
    }

    private void validateType(String type) {
        if (EmailTypeEnum.fromString(type) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email type");
        }
    }

}
