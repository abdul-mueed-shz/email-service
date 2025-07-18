package com.abdul.email.adapter.in.messaging;

import com.abdul.email.config.KafkaProperties;
import com.abdul.email.domain.email.model.SendEmailInfo;
import com.abdul.email.domain.email.usecase.AbstractSendEmailUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerListener {

    private final ApplicationContext applicationContext;
    private final KafkaProperties kafkaProperties;

    @KafkaListener(
            topics = "#{'${spring.kafka.topics.consume.email-topic}'}",
            groupId = "#{'${spring.kafka.groups.email-group}'}",
            containerFactory = "emailKafkaListenerContainerFactory"
    )
    public void listen(SendEmailInfo sendEmailInfo) {
        if (!applicationContext.containsBean(sendEmailInfo.type().getType())) {
            log.error("No bean found for type: {}, in topic: {} of group: {}",
                    sendEmailInfo.type().getType(),
                    kafkaProperties.getTopics().getConsume().getEmailTopic(),
                    kafkaProperties.getGroups().getEmailGroup());
            return;
        }
        AbstractSendEmailUseCase sendEmailUseCase =
                (AbstractSendEmailUseCase) applicationContext.getBean(sendEmailInfo.type().getType());
        sendEmailUseCase.execute(sendEmailInfo.to());
    }
}
