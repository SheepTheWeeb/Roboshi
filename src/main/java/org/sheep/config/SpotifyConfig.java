package org.sheep.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties
public class SpotifyConfig {
    @Value("${spotifyClientID}")
    private String spotifyClientID;
    @Value("${spotifyClientSecret}")
    private String spotifyClientSecret;
    @Value("${spotify.baseUrl}")
    private String baseUrl;
    @Value("${spotify.auth.endpoint}")
    private String authEndpoint;
    @Value("${spotify.token.expireTime}")
    private String tokenExpireTime;
}
