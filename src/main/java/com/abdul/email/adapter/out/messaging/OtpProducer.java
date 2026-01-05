package com.abdul.email.adapter.out.messaging;


import com.abdul.email.config.kafka.KafkaProperties;
import com.abdul.email.domain.email.model.NumericOtpInfo;
import com.abdul.email.domain.email.port.in.OtpPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true")
public class OtpProducer implements OtpPublisher {

    private final KafkaTemplate<String, NumericOtpInfo> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    @Override
    public void sendNumericOtpInfo(NumericOtpInfo numericOtpInfo) {
        log.info("Sending otp confirmation: {}", numericOtpInfo);
        Message<NumericOtpInfo> message = MessageBuilder
                .withPayload(numericOtpInfo)
                .setHeader(KafkaHeaders.TOPIC, kafkaProperties.getTopics().getProduce().getOtpTopic())
                .build();
        kafkaTemplate.send(message);
    }
}
