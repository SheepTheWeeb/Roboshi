package org.sheep.config;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.sheep.service.listener.MessageListener;
import org.sheep.service.listener.ReadyListener;
import org.sheep.util.RoboshiConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class DiscordConfig {
    private MessageListener messageListener;
    private ReadyListener readyListener;
    private RoboshiConfig config;

    @Bean
    public JDA getJDA() {
        return JDABuilder.createDefault(config.getDiscordBotToken())
                .addEventListeners(readyListener, messageListener)
                .setActivity(Activity.playing(RoboshiConstant.BOT_ACTIVITY))
                .build();
    }
}
