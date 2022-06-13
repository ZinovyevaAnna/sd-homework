package ru.itmo.simplecli.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

/**
 * Class to construct piped commands
 */
public class PipedCommandFactory extends AbstractCommandFactory {

    @Override
    public Executable construct(List<String> args, EnvironmentManager environment) {
        var lists = splitIntoCommands(args);
        var executables = new ArrayList<Executable>();
        var singleCommandFactory = new SingleCommandFactory();
        for (var list : lists) {
            var cmd = singleCommandFactory.construct(list, environment);
            if (cmd == null) {
                return null;
            }
            executables.add(cmd);
        }
        return new PipedCommand(executables);
    }

    private List<List<String>> splitIntoCommands(List<String> args) {
        List<List<String>> result = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (var element : args) {
            if (element.equals("|")) {
                result.add(current);
                current = new ArrayList<>();
            } else {
                current.add(element);
            }
        }
        result.add(current);
        return result;
    }
}