package ru.itmo.simplecli.executor.commands;

import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;

import java.util.List;

/**
 * Abstract class for an explicit command
 */
public abstract class Command implements Executable {
    protected EndStatus status;
    protected List<String> args;
    protected String output = null;
    protected EnvironmentManager environment;

    public Command(List<String> args, EnvironmentManager environment) {
        this.args = args;
        this.environment = environment;
    }

    @Override
    public String getOutput() {
        return output;
    }

    @Override
    public EndStatus getEndStatus() {
        return status;
    }
    @Override
    public abstract void execute(String input);

}