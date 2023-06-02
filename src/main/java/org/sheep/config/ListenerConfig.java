package org.sheep.config;

import org.sheep.listeners.MessageListener;
import org.sheep.listeners.ReadyListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfig {
    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }

    @Bean
    public ReadyListener readyListener() {
        return new ReadyListener();
    }
}
