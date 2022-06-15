package ru.itmo.simplecli.executor.commands;

import ru.itmo.simplecli.executor.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractCommandTest {

    public void assertSuccess(Command cmd) {
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
    }

    public void assertError(Command cmd) {
        assertEquals(Executable.EndStatus.ERROR, cmd.getEndStatus());
    }

}
