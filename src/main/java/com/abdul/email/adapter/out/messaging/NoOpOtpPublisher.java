package com.abdul.email.adapter.out.messaging;

import com.abdul.email.domain.email.model.NumericOtpInfo;
import com.abdul.email.domain.email.port.in.OtpPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpOtpPublisher implements OtpPublisher {

    @Override
    public void sendNumericOtpInfo(NumericOtpInfo numericOtpInfo) {
        log.info("Kafka is disabled. OTP info not published: {}", numericOtpInfo);
    }
}

