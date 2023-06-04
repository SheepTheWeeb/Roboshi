package org.sheep.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.sheep.model.command.AbstractCommand;
import org.sheep.model.command.PingCommand;
import org.sheep.model.command.tamagotchi.CreateCommand;
import org.sheep.model.command.tamagotchi.StatsCommand;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommandHelperImpl implements CommandHelper {
    private final List<AbstractCommand> commands = new ArrayList<>();

    // Commands
    private PingCommand pingCommand;
    private StatsCommand statsCommand;
    private CreateCommand createCommand;

    @PostConstruct
    private void setupCommands() {
        commands.add(pingCommand);
        commands.add(statsCommand);
        commands.add(createCommand);
    }

    @Override
    public List<AbstractCommand> getCommands() {
        return commands;
    }
}
