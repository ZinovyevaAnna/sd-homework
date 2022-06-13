package ru.itmo.simplecli.executor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PipedCommandFactoryTest {

    public AbstractCommandFactory instance = new PipedCommandFactory();

    @Test
    public void testPipe() {
        var env = new EnvironmentManager();
        var args = List.of(
                "cat", "|", "echo", "|", "grep", "|", "exit", "|", "pwd", "|", "wc"
        );
        assertNotNull(instance.construct(args, env));
        assertNull(instance.construct(List.of(), env));
    }

}