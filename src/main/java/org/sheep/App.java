package org.sheep;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.sheep.config.RoboshiConfig;
import org.sheep.model.command.PingCommand;
import org.sheep.service.MessageListener;
import org.sheep.service.ReadyListener;
import org.sheep.util.RoboshiConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    private RoboshiConfig config;

    public static void main(String[] args) {
        log.info("Starting Roboshi");
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(config.getDiscordBotToken())
                .addEventListeners(
                        new ReadyListener(),
                        new MessageListener())
                .setActivity(Activity.playing(RoboshiConstant.BOT_ACTIVITY))
                .build();
        jda.updateCommands()
                .addCommands(Commands.slash(PingCommand.NAME, PingCommand.DESCRIPTION))
                .queue();
        jda.awaitReady();
    }
}
