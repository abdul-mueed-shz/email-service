package com.abdul.email.adapter.out.cognito;

import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign configuration for form-urlencoded requests to Cognito.
 */
@Configuration
public class CognitoFeignConfig {

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
        return new FormEncoder(new SpringEncoder(converters));
    }
}

