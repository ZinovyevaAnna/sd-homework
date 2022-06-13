package ru.itmo.simplecli.executor.commands;

import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.List;

/**
 * Exits application
 */
public class Exit extends Command {

    public Exit(List<String> args, EnvironmentManager environment) {
        super(args, environment);
    }

    @Override
    public void execute(String input) {
        status = EndStatus.EXIT;
    }

}
