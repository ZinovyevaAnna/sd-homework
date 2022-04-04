package ru.itmo.simplecli.executor;

import java.util.ArrayList;
import java.util.List;

public class PipedCommandFactory {
    public static Executable construct(List<List<String>> args, EnvironmentManager environment) {
        var executables = new ArrayList<Executable>();
        for (var list : args) {
            var cmd = CommandFactory.construct(list, environment);
            if (cmd == null) {
                return null;
            }
            executables.add(cmd);
        }
        return new PipedCommand(executables);
    }
}