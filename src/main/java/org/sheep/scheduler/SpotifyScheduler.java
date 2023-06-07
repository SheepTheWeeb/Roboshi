package org.sheep.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.sheep.config.SpotifyConfig;
import org.sheep.gateway.SpotifyGateway;
import org.sheep.service.CacheService;
import org.sheep.util.RoboshiConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class SpotifyScheduler {

    @Autowired
    private SpotifyConfig spotifyConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CacheService<String> authCache;

    @Scheduled(cron = "0 * * * * *")
    public void songOfTheDayScheduler() {
        log.info("Song of the day scheduler started");
        String token = getCachedAuthToken();
        
    }

    private String getCachedAuthToken() {
        String token = authCache.get(RoboshiConstant.SPOTIFY_AUTH_TOKEN);
        if (token == null) {
            token = SpotifyGateway.getAuthToken(restTemplate, spotifyConfig);
            authCache.add(RoboshiConstant.SPOTIFY_AUTH_TOKEN, token);
        }
        return token;
    }
}
