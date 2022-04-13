package ru.itmo.simplecli.executor.constructors;

import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Command;

import java.util.List;

public interface CommandConstructor {
    Command construct(List<String> args, EnvironmentManager environment);
}
