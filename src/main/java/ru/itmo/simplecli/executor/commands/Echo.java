package ru.itmo.simplecli.executor.commands;

import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.List;

/**
 * Returns given args without changes
 */
public class Echo extends Command {

    public Echo(List<String> args, EnvironmentManager environment) {
        super(args, environment);
    }

    @Override
    public void execute(String input) {
        output = String.join(" ", args);
        status = EndStatus.SUCCESS;
    }

}