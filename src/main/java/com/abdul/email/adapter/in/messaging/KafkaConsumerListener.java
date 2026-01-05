package com.abdul.email.adapter.in.messaging;

import com.abdul.email.config.kafka.KafkaProperties;
import com.abdul.email.domain.email.model.EmailKafkaListenerDto;
import com.abdul.email.domain.email.model.EmailRequest;
import com.abdul.email.domain.email.port.in.AbstractInitEmailUseCase;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true")
public class KafkaConsumerListener {

    private final ApplicationContext applicationContext;
    private final KafkaProperties kafkaProperties;

    @KafkaListener(
            topics = "#{'${spring.kafka.topics.consume.email-topic}'}",
            groupId = "#{'${spring.kafka.groups.email-group}'}",
            containerFactory = "emailKafkaListenerContainerFactory"
    )
    public void listen(EmailKafkaListenerDto sendEmailRequest) throws MessagingException {
        if (!applicationContext.containsBean(sendEmailRequest.type().getType())) {
            log.error("No bean found for type: {}, in topic: {} of group: {}",
                    sendEmailRequest.type().getType(),
                    kafkaProperties.getTopics().getConsume().getEmailTopic(),
                    kafkaProperties.getGroups().getEmailGroup());
            return;
        }
        AbstractInitEmailUseCase sendEmailUseCase =
                (AbstractInitEmailUseCase) applicationContext.getBean(sendEmailRequest.type().getType());
        sendEmailUseCase.execute(EmailRequest.builder()
                .toEmail(sendEmailRequest.to())
                .build());
    }
}
