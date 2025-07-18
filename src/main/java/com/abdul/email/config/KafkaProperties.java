package com.abdul.email.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    private String bootstrapServers;

    private Group groups;

    private Topics topics;

    @Data
    public static class Group {

        private String emailGroup;
        private String otpGroup;
    }

    @Data
    public static class Topics {

        private Consume consume;
        private Produce produce;

        @Data
        public static class Consume {

            private String emailTopic;
        }

        @Data
        public static class Produce {

            private String otpTopic;

            public List<String> getTopics() {
                return List.of(otpTopic);
            }
        }
    }
}
