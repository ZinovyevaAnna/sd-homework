package ru.itmo.simplecli.executor.commands;

import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.List;

/**
 * Changes local variable value
 */
public class Assignment extends Command {
    public Assignment(List<String> args, EnvironmentManager environment) {
        super(args, environment);
    }

    @Override
    public void execute(String input) {
        environment.set(args.get(0), args.get(2));
    }
}