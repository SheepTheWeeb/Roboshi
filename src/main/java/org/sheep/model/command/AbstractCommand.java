package org.sheep.model.command;

import lombok.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public abstract class AbstractCommand {
    private String name;
    private String description;
    private List<OptionData> options;

    public abstract void execute(SlashCommandInteractionEvent event);
}
