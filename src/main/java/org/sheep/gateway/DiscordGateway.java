package org.sheep.gateway;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.sheep.model.command.AbstractCommand;

import java.util.List;

@Slf4j
public class DiscordGateway {
    private DiscordGateway() {}

    public static void deleteCommands(JDA jda) {
        log.info("Deleting old commands...");
        jda.retrieveCommands()
                .complete()
                .forEach(command -> command.delete().complete());
        log.info("Commands deleted");
    }

    public static void updateCommands(JDA jda, List<AbstractCommand> commandList) {
        log.info("Updating commands...");
        CommandListUpdateAction commandListUpdateAction = jda.updateCommands();
        for (AbstractCommand command : commandList) {
            commandListUpdateAction = commandListUpdateAction.addCommands(
                    Commands.slash(command.getName(), command.getDescription())
            );
        }
        commandListUpdateAction.queue();
        log.info("Commands updated");
    }
}
