package org.sheep.service;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.sheep.model.command.AbstractCommand;
import org.sheep.model.command.PingCommand;

import java.util.ArrayList;
import java.util.List;

public class MessageListener extends ListenerAdapter {
    private final List<AbstractCommand> commands;

    public MessageListener() {
        commands = new ArrayList<>();
        commands.add(new PingCommand());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (AbstractCommand command : commands) {
            if (event.getName().equals(command.getName())) {
                command.execute(event);
            }
        }
    }
}
