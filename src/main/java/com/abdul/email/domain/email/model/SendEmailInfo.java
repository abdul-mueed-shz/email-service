package com.abdul.email.domain.email.model;

import com.abdul.email.domain.email.enums.EmailTypeEnum;

public record SendEmailInfo(
        String to,
        EmailTypeEnum type
) {

}
