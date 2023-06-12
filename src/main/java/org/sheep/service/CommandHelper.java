package org.sheep.service;

import org.sheep.model.command.AbstractCommand;

import java.util.List;

public interface CommandHelper {
    List<AbstractCommand> getCommands();
}
