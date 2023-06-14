package org.sheep.model.command;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class PingCommand extends AbstractCommand {
    private static final String NAME = "ping";
    private static final String DESCRIPTION = "Calculate response-time of the bot";

    public PingCommand() {
        super(NAME, DESCRIPTION, new ArrayList<>());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong!")
                .setEphemeral(true)
                .flatMap(v -> event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time))
                .queue();
    }
}
