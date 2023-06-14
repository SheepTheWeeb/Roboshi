package org.sheep.model.command.tamagotchi;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.apache.commons.lang3.StringUtils;
import org.sheep.model.command.AbstractCommand;
import org.sheep.model.db.Tamagotchi;
import org.sheep.repository.TamagotchiRepository;
import org.sheep.util.RoboshiConstant;
import org.sheep.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CreateCommand extends AbstractCommand {
    private static final String NAME = "create";
    private static final String DESCRIPTION = "Create Tamagotchi";

    // options
    private static final String NAME_OPTION = "name";

    private final TamagotchiRepository tamagotchiRepository;

    @Autowired
    public CreateCommand(TamagotchiRepository tamagotchiRepository) {
        super(NAME, DESCRIPTION, new ArrayList<>());
        super.getOptions().add(new OptionData(OptionType.STRING, NAME_OPTION, "Name of your tamagotchi", true));
        this.tamagotchiRepository = tamagotchiRepository;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String name = Objects.requireNonNull(event.getOption(NAME_OPTION), "Name option was null").getAsString();
        if (name.isBlank()) {
            event.reply("Your Tamagotchi needs a name.").queue();
            return;
        }

        Tamagotchi tamagotchi = tamagotchiRepository.findFirstByOrderByCreatedDesc();
        if (tamagotchi != null && tamagotchi.getHp() > 0) {
            event.reply("Your Tamagotchi is still alive! Please use '/stats' to view it's stats.").queue();
            return;
        }

        Tamagotchi newTamagotchi = Tamagotchi.builder()
                .name(name)
                .imageName("egg1.png")
                .hp(RoboshiConstant.MAX_HP)
                .hunger(RoboshiConstant.MAX_HUNGER)
                .happiness(0)
                .needsToilet(false)
                .isSick(false)
                .age(0)
                .created(TimeUtil.getCurrentTime())
                .build();
        tamagotchiRepository.save(newTamagotchi);
        event.reply("New Tamagotchi created named '" + name + "'!").queue();
    }
}
