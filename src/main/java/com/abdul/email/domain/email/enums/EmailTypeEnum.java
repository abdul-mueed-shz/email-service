package com.abdul.email.domain.email.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTypeEnum {
    @JsonProperty("otp")
    OTP("otp");

    private final String type;
}
