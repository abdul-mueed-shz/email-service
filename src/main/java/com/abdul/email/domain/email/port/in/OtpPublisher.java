package com.abdul.email.domain.email.port.in;

import com.abdul.email.domain.email.model.NumericOtpInfo;

public interface OtpPublisher {
    void sendNumericOtpInfo(NumericOtpInfo numericOtpInfo);
}

