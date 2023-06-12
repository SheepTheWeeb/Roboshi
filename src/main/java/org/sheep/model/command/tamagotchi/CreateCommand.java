package org.sheep.model.command.tamagotchi;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.sheep.model.command.AbstractCommand;
import org.sheep.model.db.Tamagotchi;
import org.sheep.repository.TamagotchiRepository;
import org.sheep.util.RoboshiConstant;
import org.sheep.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCommand extends AbstractCommand {
    private static final String NAME = "create";
    private static final String DESCRIPTION = "Create Tamagotchi";

    private final TamagotchiRepository tamagotchiRepository;

    @Autowired
    public CreateCommand(TamagotchiRepository tamagotchiRepository) {
        super(NAME, DESCRIPTION, true);
        this.tamagotchiRepository = tamagotchiRepository;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!this.isEnabled()) {
            log.warn("CreateCommand is disabled but still used");
            return;
        }

        Tamagotchi tamagotchi = tamagotchiRepository.findFirstByOrderByCreatedDesc();
        if (tamagotchi != null && tamagotchi.getHp() > 0) {
            event.reply("Your Tamagotchi is still alive! Please use '/stats' to view it's stats.").queue();
            return;
        }

        Tamagotchi newTamagotchi = Tamagotchi.builder()
                .hp(RoboshiConstant.MAX_HP)
                .hunger(RoboshiConstant.MAX_HUNGER)
                .created(TimeUtil.getCurrentTime())
                .build();
        tamagotchiRepository.save(newTamagotchi);
        event.reply("New Tamagotchi created!").queue();
    }
}
