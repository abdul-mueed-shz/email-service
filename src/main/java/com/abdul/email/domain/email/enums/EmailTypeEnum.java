package com.abdul.email.domain.email.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTypeEnum {
    @JsonProperty("otp")
    OTP("otp"),
    @JsonProperty("text")
    TEXT("text"),
    @JsonProperty("contact")
    CONTACT("contact");

    private final String type;

    public static EmailTypeEnum fromString(String type) {
        for (EmailTypeEnum emailTypeEnum : EmailTypeEnum.values()) {
            if (emailTypeEnum.getType().equals(type)) {
                return emailTypeEnum;
            }
        }
        return null;
    }
}
