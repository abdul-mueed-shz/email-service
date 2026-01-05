package com.abdul.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Security security;
    private Email email;

    @Data
    public static class Security {
        private boolean enabled;
    }

    @Data
    public static class Email {
        private List<String> adminRecipients;
    }
}
