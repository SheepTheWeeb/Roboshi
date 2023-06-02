package org.sheep.model.command.tamagotchi;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.sheep.model.command.AbstractCommand;
import org.sheep.util.RoboshiConstant;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.OffsetDateTime;

@Slf4j
public class StatsCommand extends AbstractCommand {
    private static final String NAME = "stats";
    private static final String DESCRIPTION = "Get tamagotchi stats";

    public StatsCommand() {
        super(NAME, DESCRIPTION, true);
    }
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!this.isEnabled()) {
            log.warn("PingCommand is disabled but still used");
            return;
        }

        // TODO: Fetch HP and Hunger from a database
        try {
            InputStream file = new URL(RoboshiConstant.MELON_DOG_IMG).openStream();
            event.replyEmbeds(new EmbedBuilder()
                        .setTitle("Roboshi stats")
                        .setDescription(RoboshiConstant.BOT_ACTIVITY)
                        .setImage("attachment://melondog.webp")
                        .setFooter("Roboshi", RoboshiConstant.MELON_DOG_IMG)
                        .setColor(new Color(0xe5642d))
                        .setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl())
                        .setTimestamp(OffsetDateTime.now())
                        .addField("HP", "text", false)
                        .addField("Hunger", "text", false)
                        .build())
                    .addFiles(FileUpload.fromData(file, "melondog.webp"))
                    .queue();
        } catch (IOException ex) {
            log.error("IOException has occurred: {}", ex, ex);
        }
    }
}
