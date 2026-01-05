package com.abdul.email.adapter.out.cognito;

import com.abdul.email.domain.auth.model.CognitoTokenRequest;
import com.abdul.email.domain.auth.model.CognitoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client for AWS Cognito OAuth2 token endpoint.
 */
@FeignClient(
        name = "cognito-client",
        url = "${spring.security.oauth2.client.provider.cognito.token-uri}",
        configuration = CognitoFeignConfig.class
)
public interface CognitoFeignClient {

    /**
     * Fetches an OAuth2 access token using client credentials grant.
     *
     * @param request the token request containing client credentials
     * @return CognitoTokenResponse with access token and metadata
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    CognitoTokenResponse getToken(@RequestBody CognitoTokenRequest request);
}
