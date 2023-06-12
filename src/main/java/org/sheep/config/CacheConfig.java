package org.sheep.config;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.sheep.service.CacheService;
import org.sheep.service.CacheServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
public class CacheConfig {

    private SpotifyConfig spotifyConfig;

    @Bean
    public CacheService<String> tokenCache() {
        return new CacheServiceImpl<>(NumberUtils.toInt(spotifyConfig.getTokenExpireTime(), 3600), TimeUnit.SECONDS);
    }
}
