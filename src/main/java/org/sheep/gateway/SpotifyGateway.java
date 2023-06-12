package org.sheep.gateway;

import lombok.extern.slf4j.Slf4j;
import org.sheep.config.SpotifyConfig;
import org.sheep.model.response.spotify.SpotifyItem;
import org.sheep.model.response.spotify.SpotifyListing;
import org.sheep.model.response.spotify.SpotifyTokenResponse;
import org.sheep.util.RoboshiConstant;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
                    config.getAuthBaseUrl() + config.getAuthEndpoint(),
                    HttpMethod.POST,
                    entity,
                    SpotifyTokenResponse.class
            );
            SpotifyTokenResponse body = response.getBody();
            if (body != null && response.getStatusCode().is2xxSuccessful()) {
                return body.getAccessToken();
            }
        } catch(RestClientResponseException | ResourceAccessException ex) {
            log.error("Error has occurred while fetching auth token: {}", ex.getMessage(), ex);
        }
        return "Could not fetch auth token from Spotify API.";
    }

    public static SpotifyListing<SpotifyItem> getTracks(String playlistID, int offset, String token, RestTemplate restTemplate, SpotifyConfig config) {
        String tracksEndpoint = String.format(config.getTracksEndpoint(), playlistID);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            URI uri = UriComponentsBuilder.fromUriString(config.getApiBaseUrl() + tracksEndpoint)
                    .queryParam(RoboshiConstant.FIELDS, "total, items(track(id))")
                    .queryParam(RoboshiConstant.OFFSET, offset)
                    .queryParam(RoboshiConstant.LIMIT, config.getTracksLimit())
                    .build()
                    .toUri();
            ResponseEntity<SpotifyListing<SpotifyItem>> response = restTemplate.exchange(
                     uri,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>(){}
            );
            SpotifyListing<SpotifyItem> body = response.getBody();
            if (body != null && response.getStatusCode().is2xxSuccessful()) {
                return body;
            }
        } catch(Exception ex) {
            log.error("Error has occurred while fetching a spotify playlist tracks by playlistID [{}], ex: {}", playlistID, ex.getMessage(), ex);
            // TODO: Exception handling
        }
        return null;
    }
}
