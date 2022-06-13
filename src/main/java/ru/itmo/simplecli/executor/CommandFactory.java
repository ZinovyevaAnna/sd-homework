package ru.itmo.simplecli.executor;

import ru.itmo.simplecli.executor.commands.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Class to construct commands
 */
public class CommandFactory {
    public static Executable construct(List<String> args, EnvironmentManager environment) {
        if (args.size() == 0) {
            return null;
        }
        if (isAssignment(args)) {
            return new Assignment(args, environment);
        }
        var name = args.get(0);
        return switch (name) {
            case "cat" -> new Cat(args.subList(1, args.size()), environment);
            case "echo" -> new Echo(args.subList(1, args.size()), environment);
            case "exit" -> new Exit(args.subList(1, args.size()), environment);
            case "pwd" -> new Pwd(args.subList(1, args.size()), environment);
            case "wc" -> new WC(args.subList(1, args.size()), environment);
            default -> new External(args, environment);
        };
    }

    private static boolean isAssignment(List<String> args) {
        return args.size() == 3
                && Pattern.matches("\\w+", args.get(0))
                && args.get(1).equals("=")
                && Pattern.matches("\\w+", args.get(2));
    }
}
