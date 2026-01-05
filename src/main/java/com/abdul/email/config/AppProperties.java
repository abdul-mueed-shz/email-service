package com.abdul.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Security security;

    @Data
    public static class Security {
        private boolean enabled;
    }
}
