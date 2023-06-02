package org.sheep.listeners;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.sheep.gateway.DiscordGateway;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ReadyListener implements EventListener {
    @Autowired
    private MessageListener messageListener;

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            // Update slash command list
            JDA jda = event.getJDA();
            DiscordGateway.deleteCommands(jda);
            DiscordGateway.updateCommands(jda, messageListener.getCommands());
            log.info("API is ready!");
        }
    }
}
