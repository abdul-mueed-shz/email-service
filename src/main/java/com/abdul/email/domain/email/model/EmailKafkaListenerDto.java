package com.abdul.email.domain.email.model;

import com.abdul.email.domain.email.enums.EmailTypeEnum;

public record EmailKafkaListenerDto(
        String to,
        EmailTypeEnum type
) {

}
