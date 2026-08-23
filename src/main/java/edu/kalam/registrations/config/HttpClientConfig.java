package edu.kalam.registrations.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Outbound HTTP client configuration.
 */
@Configuration
public class HttpClientConfig {

    @Bean
    public RestClient restClient(
            @Value("${app.http-client.base-url:http://localhost:8080}") String baseUrl) {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }
}
