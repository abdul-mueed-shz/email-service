package com.abdul.email.domain.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessageInfo<T> extends BaseResponseMessageInfo {
    private T data;

    public ResponseMessageInfo(T data) {
        super();
        this.data = data;
    }
}
