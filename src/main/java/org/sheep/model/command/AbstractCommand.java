package org.sheep.model.command;

import lombok.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public abstract class AbstractCommand {
    private String name;
    private String description;
    private boolean enabled;

    public abstract void execute(SlashCommandInteractionEvent event);
}
