package org.sheep.gateway;

import lombok.extern.slf4j.Slf4j;
import org.sheep.config.SpotifyConfig;
import org.sheep.model.response.ComplimentResponse;
import org.sheep.model.response.SpotifyTokenResponse;
import org.sheep.util.RoboshiConstant;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SpotifyGateway {
    private SpotifyGateway() {
    }

    /**
     * Fetches Spotify API auth token
     *
     * @param restTemplate RestTemplate
     * @param config Spotify configurations
     * @return Auth token
     */
    public static String getAuthToken(RestTemplate restTemplate, SpotifyConfig config) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
            formParams.add(RoboshiConstant.GRANT_TYPE, "client_credentials");
            formParams.add(RoboshiConstant.CLIENT_ID, config.getSpotifyClientID());
            formParams.add(RoboshiConstant.CLIENT_SECRET, config.getSpotifyClientSecret());
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParams, headers);
            ResponseEntity<SpotifyTokenResponse> response = restTemplate.exchange(
                    config.getBaseUrl() + config.getAuthEndpoint(),
                    HttpMethod.POST,
                    entity,
                    SpotifyTokenResponse.class
            );
            SpotifyTokenResponse body = response.getBody();
            if (body != null && response.getStatusCode().is2xxSuccessful()) {
                return body.getAccess_token();
            }
        } catch(RestClientResponseException | ResourceAccessException ex) {
            log.error("Error has occurred while fetching auth token: {}", ex.getMessage(), ex);
        }
        return "Could not fetch auth token from Spotify API.";
    }
}
