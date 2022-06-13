package ru.itmo.simplecli.executor.commands;

import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.List;

/**
 * Returns PWD value
 */
public class Pwd extends Command {

    public Pwd(List<String> args, EnvironmentManager environment) {
        super(args, environment);
    }

    @Override
    public void execute(String string) {
        output = System.getProperty("user.dir");
        status = EndStatus.SUCCESS;
    }
}
