package ru.itmo.simplecli.executor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PipedCommandFactoryTest {

    @Test
    void testPipe() {
        var env = new EnvironmentManager();
        var args = List.of(List.of("cat"), List.of("echo"),
                List.of("grep"), List.of("exit"), List.of("pwd"), List.of("wc"));
        assertNotNull(PipedCommandFactory.construct(args, env));
        assertNull(PipedCommandFactory.construct(List.of(new ArrayList<>()), env));
    }

}