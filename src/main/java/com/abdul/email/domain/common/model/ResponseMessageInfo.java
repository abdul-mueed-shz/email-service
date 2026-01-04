package com.abdul.email.domain.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseMessageInfo {
    private String message;

    public ResponseMessageInfo() {
        this.message = "Action successful!";
    }
}
