package org.sheep.service.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.sheep.model.command.AbstractCommand;
import org.sheep.service.CommandHelper;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
public class MessageListener extends ListenerAdapter {
    private CommandHelper commandHelper;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (AbstractCommand command : commandHelper.getCommands()) {
            if (event.getName().equals(command.getName())) {
                command.execute(event);
            }
        }
    }
}
