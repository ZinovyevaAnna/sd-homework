package ru.itmo.simplecli.executor.constructors;

import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Command;

import java.util.List;

public class Exit implements CommandConstructor {
    @Override
    public Command construct(List<String> args, EnvironmentManager environment) {
        return new Command(args, environment) {
            @Override
            public void execute(String input) {
                status = EndStatus.EXIT;
            }
        };
    }
}
