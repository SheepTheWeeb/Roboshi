package org.sheep.model.command.tamagotchi;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.apache.commons.lang3.StringUtils;
import org.sheep.model.Happiness;
import org.sheep.model.command.AbstractCommand;
import org.sheep.model.db.Tamagotchi;
import org.sheep.repository.TamagotchiRepository;
import org.sheep.util.ImageUtil;
import org.sheep.util.RoboshiConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
        FileUpload image = getImage(tamagotchi.getImageName());
        if (image != null) {
            event.replyEmbeds(createMessage(event, tamagotchi))
                    .addFiles(image)
                    .queue();
        } else {
            event.reply("Brokko bot").queue();
        }
    }

    private FileUpload getImage(String name) {
        try {
            Image image = ImageUtil.getImageFromResources(name, RoboshiConstant.TAMAGOTCHI_IMG_RESOURCE_PATH);
            BufferedImage resizedImage = ImageUtil.resize(image , 1000, 1000);
            InputStream file = ImageUtil.convertBufferedImageToInputStream(resizedImage);
            return FileUpload.fromData(file, "tamagotchi.png");
        } catch(IOException ex) {
            log.error("IOException has occurred: {}", ex.getMessage(), ex);
        }
        return null;
    }

    private MessageEmbed createMessage(SlashCommandInteractionEvent event, Tamagotchi tamagotchi) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(tamagotchi.getName() + "'s stats")
                .setImage("attachment://tamagotchi.png")
                .setFooter("Roboshi", RoboshiConstant.MELON_DOG_IMG)
                .setColor(new Color(0xe5642d))
                .setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl())
                .setTimestamp(OffsetDateTime.now())
                .addField("HP", tamagotchi.getHp() + "/" + RoboshiConstant.MAX_HP, true)
                .addField("Hunger", tamagotchi.getHunger() + "/" + RoboshiConstant.MAX_HUNGER, true)
                .addField("Happiness", StringUtils.capitalize(Happiness.getHappiness(tamagotchi.getHappiness())
                        .name().toLowerCase()), true)
                .addField("Age", String.valueOf(tamagotchi.getAge()), true)
                .addField("Needs to take a dump?", tamagotchi.isNeedsToilet() ? "Yes" : "No", true)
                .addField("Sick?", tamagotchi.isSick() ? "Yes" : "No", true);
        setDescription(embedBuilder, tamagotchi.getHp());
        return embedBuilder.build();
    }

    private void setDescription(EmbedBuilder embedBuilder, int hp) {
        if (hp > 0) {
            embedBuilder.setDescription(RoboshiConstant.BOT_ACTIVITY);
        } else {
            embedBuilder.setDescription("Not looking for watermelons anymore... It ~~died~~ went on vacation \uD83D\uDC80. You can use '/create' to adopt a new one.");
        }
    }
}
