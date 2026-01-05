package com.abdul.email.domain.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponseMessageInfo {
    private String message;

    public BaseResponseMessageInfo() {
        this.message = "Action successful!";
    }
}
