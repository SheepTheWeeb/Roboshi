package org.sheep.model.command.tamagotchi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.sheep.model.TamagotchiVariant;
import org.sheep.model.command.AbstractCommand;
import org.sheep.model.db.Tamagotchi;
import org.sheep.repository.TamagotchiRepository;
import org.sheep.util.FileUtil;
import org.sheep.util.RoboshiConstant;
import org.sheep.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
public class CreateCommand extends AbstractCommand {
    private static final String NAME = "create";
    private static final String DESCRIPTION = "Create Tamagotchi";

    // options
    private static final String NAME_OPTION = "name";

    private static final String TAMAGOTCHI_VARIANTS_FILE_NAME = "tamagotchi_variants.json";

    private final TamagotchiRepository tamagotchiRepository;
    private final ObjectMapper mapper;
    private final Random random;

    @Autowired
    public CreateCommand(TamagotchiRepository tamagotchiRepository, ObjectMapper mapper, Random random) {
        super(NAME, DESCRIPTION, new ArrayList<>());
        super.getOptions().add(new OptionData(OptionType.STRING, NAME_OPTION, "Name of your tamagotchi", true));
        this.tamagotchiRepository = tamagotchiRepository;
        this.mapper = mapper;
        this.random = random;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String name = Objects.requireNonNull(event.getOption(NAME_OPTION), "Name option was null").getAsString();
        Tamagotchi tamagotchi = tamagotchiRepository.findFirstByOrderByCreatedDesc();
        if (tamagotchi != null && tamagotchi.getHp() > 0) {
            event.reply("Your Tamagotchi is still alive! Please use '/stats' to view it's stats.").queue();
            return;
        }
        TamagotchiVariant variant = Objects.requireNonNull(getRandomEgg(), "Variant was null");
        saveTamagotchi(name, variant);
        event.reply("New Tamagotchi created named '" + name + "'!").queue();
    }

    private TamagotchiVariant getRandomEgg() {
        try {
            List<TamagotchiVariant> tamagotchiVariants = FileUtil.getTamagotchiVariants(TAMAGOTCHI_VARIANTS_FILE_NAME, "tamagotchi/", mapper)
                    .stream()
                    .filter(variant -> variant.getType().equals("egg"))
                    .toList();
            return tamagotchiVariants.get(random.nextInt(tamagotchiVariants.size()));
        } catch (IOException ex) {
            log.error("Could not read from: {}", TAMAGOTCHI_VARIANTS_FILE_NAME, ex);
        }
        return null;
    }

    private void saveTamagotchi(String name, TamagotchiVariant variant) {
        Tamagotchi newTamagotchi = Tamagotchi.builder()
                .variantId(variant.getId())
                .name(name)
                .imageName(variant.getImageName())
                .hp(RoboshiConstant.MAX_HP)
                .hunger(RoboshiConstant.MAX_HUNGER)
                .happiness(0)
                .needsToilet(false)
                .isSick(false)
                .age(0)
                .created(TimeUtil.getCurrentTime())
                .build();
        tamagotchiRepository.save(newTamagotchi);
    }
}
