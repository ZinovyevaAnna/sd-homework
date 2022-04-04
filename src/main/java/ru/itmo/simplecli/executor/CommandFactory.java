package ru.itmo.simplecli.executor;

import ru.itmo.simplecli.executor.constructors.*;

import java.util.List;

public class CommandFactory {
    public static Executable construct(List<String> args, EnvironmentManager environment) {
        if (args.size() == 0) {
            return null;
        }
        var name = args.get(0);
        return switch (name) {
            case "cat" -> new Cat().construct(args.subList(1, args.size()), environment);
            case "echo" -> new Echo().construct(args.subList(1, args.size()), environment);
            case "exit" -> new Exit().construct(args.subList(1, args.size()), environment);
            case "grep" -> new Grep().construct(args.subList(1, args.size()), environment);
            case "pwd" -> new Pwd().construct(args.subList(1, args.size()), environment);
            case "wc" -> new WC().construct(args.subList(1, args.size()), environment);
            default -> new External().construct(args, environment);
        };
    }
}
