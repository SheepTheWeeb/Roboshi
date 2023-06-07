package org.sheep.model.command;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.sheep.config.RoboshiConfig;
import org.sheep.gateway.ComplimentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ComplimentCommand extends AbstractCommand {
    private static final String NAME = "compliment";
    private static final String DESCRIPTION = "Gives you a compliment";

    private final RoboshiConfig config;
    private final RestTemplate restTemplate;

    @Autowired
    public ComplimentCommand(RoboshiConfig config, RestTemplate restTemplate) {
        super(NAME, DESCRIPTION, true);
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!this.isEnabled()) {
            log.warn("PingCommand is disabled but still used");
            return;
        }
        String compliment = ComplimentGateway.getCompliment(restTemplate, config);
        event.reply(compliment).queue();
    }
}
