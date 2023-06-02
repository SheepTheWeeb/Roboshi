package org.sheep;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.sheep.config.RoboshiConfig;
import org.sheep.listeners.MessageListener;
import org.sheep.listeners.ReadyListener;
import org.sheep.util.RoboshiConstant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@AllArgsConstructor
@SpringBootApplication
public class App implements CommandLineRunner {
    private RoboshiConfig config;
    private MessageListener messageListener;
    private ReadyListener readyListener;

    public static void main(String[] args) {
        log.info("Starting Roboshi");
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(config.getDiscordBotToken())
                .addEventListeners(readyListener, messageListener)
                .setActivity(Activity.playing(RoboshiConstant.BOT_ACTIVITY))
                .build();
        jda.awaitReady();
    }
}
