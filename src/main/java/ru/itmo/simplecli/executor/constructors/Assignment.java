package ru.itmo.simplecli.executor.constructors;

import ru.itmo.simplecli.executor.Command;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.List;

/**
 * Changes local variable value
 */
public class Assignment implements CommandConstructor {
    @Override
    public Command construct(List<String> args, EnvironmentManager environment) {
        return new Command(args, environment) {
            @Override
            public void execute(String input) {
                environment.set(args.get(0), args.get(2));
                status = EndStatus.SUCCESS;
            }
        };
    }

}