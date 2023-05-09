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

import java.io.IOException;

@Slf4j
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        RoboshiConfig config = getConfig(args);
        if (config == null) {
            return;
        }
        JDA jda = JDABuilder.createDefault(config.getDiscordBotToken())
                .addEventListeners(
                        new ReadyListener(),
                        new MessageListener()
                ).setActivity(Activity.playing(RoboshiConstant.BOT_ACTIVITY))
                .build();

        jda.updateCommands().addCommands(
                Commands.slash(PingCommand.NAME, PingCommand.DESCRIPTION)
        ).queue();

        jda.awaitReady();
    }

    /**
     * Fetches the properties from the property files
     *
     * @param args Commandline arguments
     * @return Application configuration
     */
    private static RoboshiConfig getConfig(String[] args) {
        String env = RoboshiConstant.DEFAULT_ENV_NAME;
        if (args.length > 0) {
            env = args[0];
        }

        try {
            return new RoboshiConfig(env);
        } catch (IOException ex) {
            log.error("Could not load in property file for env: {}", env);
            return null;
        }
    }
}
