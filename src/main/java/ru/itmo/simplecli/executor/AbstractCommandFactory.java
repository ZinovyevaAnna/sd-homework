package ru.itmo.simplecli.executor;

import java.util.List;
import java.util.regex.Pattern;

public abstract class AbstractCommandFactory {
    public abstract Executable construct(List<String> args, EnvironmentManager environment);

    protected boolean isAssignment(List<String> args) {
        return args.size() == 3
            && Pattern.matches("\\w+", args.get(0))
            && args.get(1).equals("=")
            && Pattern.matches("\\w+", args.get(2));
    }
}
