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
    @Value("${spotify.client.id}")
    private String spotifyClientID;
    @Value("${spotify.client.secret}")
    private String spotifyClientSecret;
    @Value("${spotify.auth.baseUrl}")
    private String authBaseUrl;
    @Value("${spotify.auth.endpoint}")
    private String authEndpoint;
    @Value("${spotify.token.expireTime}")
    private String tokenExpireTime;
    @Value("${spotify.api.baseUrl}")
    private String apiBaseUrl;
    @Value("${spotify.tracks.endpoint}")
    private String tracksEndpoint;
    @Value("${spotify.tracks.limit}")
    private String tracksLimit;
    @Value("${spotify.tracks.maxOffset}")
    private String tracksMaxOffset;
    @Value("${discord.songOfTheDay.guild}")
    private String guild;
    @Value("${discord.songOfTheDay.channel}")
    private String channel;
}
