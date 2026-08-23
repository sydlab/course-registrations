package edu.kalam.registrations.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Thin wrapper for outbound HTTP calls.
 * TODO: replace stubs with real remote endpoints when known.
 */
@Component
public class ExternalApiClient {

    private final RestClient restClient;

    public ExternalApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * TODO: implement real outbound GET once the remote API is known.
     */
    public String getExample(String path) {
        // return restClient.get().uri(path).retrieve().body(String.class);
        return null;
    }
}
