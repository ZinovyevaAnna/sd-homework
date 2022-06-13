package ru.itmo.simplecli.executor.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest extends AbstractCommandTest {

    @Test
    void testOneArg() {
        var env = new EnvironmentManager();
        var cmd = new Echo(List.of("arg"), env);
        cmd.execute(null);
        assertSuccess(cmd);
        assertEquals("arg", cmd.getOutput());
    }

    @Test
    void testMultipleArg() {
        var env = new EnvironmentManager();
        var cmd = new Echo(List.of("arg1", "arg2"), env);
        cmd.execute(null);
        assertSuccess(cmd);
        assertEquals("arg1 arg2", cmd.getOutput());
    }

    @Test
    void testNonemptyInput() {
        var env = new EnvironmentManager();
        var cmd = new Echo(List.of("arg"), env);
        cmd.execute("something");
        assertSuccess(cmd);
        assertEquals("arg", cmd.getOutput());
    }

    @Test
    void testNoArg() {
        var env = new EnvironmentManager();
        var cmd = new Echo(new ArrayList<>(), env);
        cmd.execute(null);
        assertSuccess(cmd);
        assertEquals("", cmd.getOutput());
    }

}