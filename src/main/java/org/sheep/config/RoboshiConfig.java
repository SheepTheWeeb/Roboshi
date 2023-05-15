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
public class RoboshiConfig {
    @Value("${discordBotToken}")
    private String discordBotToken;
    @Value("${compliment.api.endpoint}")
    private String complimentAPIEndpoint;
}
