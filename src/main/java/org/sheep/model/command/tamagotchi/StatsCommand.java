package org.sheep.model.command.tamagotchi;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.sheep.model.command.AbstractCommand;
import org.sheep.model.db.Tamagotchi;
import org.sheep.repository.TamagotchiRepository;
import org.sheep.util.RoboshiConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.ArrayList;

@Slf4j
@Service
public class StatsCommand extends AbstractCommand {
    private static final String NAME = "stats";
    private static final String DESCRIPTION = "Get tamagotchi stats";

    private final TamagotchiRepository tamagotchiRepository;

    @Autowired
    public StatsCommand(TamagotchiRepository tamagotchiRepository) {
        super(NAME, DESCRIPTION, new ArrayList<>());
        this.tamagotchiRepository = tamagotchiRepository;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Tamagotchi tamagotchi = tamagotchiRepository.findFirstByOrderByCreatedDesc();
        if (tamagotchi == null) {
            event.reply("You need to create a Tamagotchi first. Please use '/create'.").queue();
            return;
        }

        replyMessageEmbed(event, tamagotchi);
    }

    private void replyMessageEmbed(SlashCommandInteractionEvent event, Tamagotchi tamagotchi) {
        try {
            InputStream file = new URL(RoboshiConstant.MELON_DOG_IMG).openStream();
            event.replyEmbeds(createMessage(event, tamagotchi))
                    .addFiles(FileUpload.fromData(file, "melondog.webp"))
                    .queue();
        } catch (IOException ex) {
            log.error("IOException has occurred: {}", ex, ex);
            event.reply("").queue();
        }
    }

    private MessageEmbed createMessage(SlashCommandInteractionEvent event, Tamagotchi tamagotchi) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Roboshi stats")
                .setImage("attachment://melondog.webp")
                .setFooter("Roboshi", RoboshiConstant.MELON_DOG_IMG)
                .setColor(new Color(0xe5642d))
                .setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl())
                .setTimestamp(OffsetDateTime.now())
                .addField("HP", String.format("%d/%d", tamagotchi.getHp(), RoboshiConstant.MAX_HP), false)
                .addField("Hunger", String.format("%d/%d", tamagotchi.getHunger(), RoboshiConstant.MAX_HUNGER), false);

        if (tamagotchi.getHp() > 0) {
            embedBuilder.setDescription(RoboshiConstant.BOT_ACTIVITY);
        } else {
            embedBuilder.setDescription("Not looking for watermelons anymore... It ~~died~~ went on vacation \uD83D\uDC80. You can use '/create' to adopt a new one.");
        }

        return embedBuilder.build();
    }
}
