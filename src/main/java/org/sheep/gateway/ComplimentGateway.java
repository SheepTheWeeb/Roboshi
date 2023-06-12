package org.sheep.gateway;

import lombok.extern.slf4j.Slf4j;
import org.sheep.config.RoboshiConfig;
import org.sheep.model.response.ComplimentResponse;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ComplimentGateway {
    private ComplimentGateway() {}

    /**
     * Fetches compliment from the compliment API
     *
     * @return compliment
     */
    public static String getCompliment(RestTemplate restTemplate, RoboshiConfig config) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ComplimentResponse> response = restTemplate.exchange(
                    config.getComplimentAPIEndpoint(),
                    HttpMethod.GET,
                    entity,
                    ComplimentResponse.class
            );
            ComplimentResponse body = response.getBody();
            if (body != null && response.getStatusCode().is2xxSuccessful()) {
                return body.getCompliment();
            }
        } catch(RestClientResponseException | ResourceAccessException ex) {
            log.error("Error has occurred while fetching compliment: {}", ex.getMessage(), ex);
        }
        return "Could not fetch compliment from API.";
    }
}
