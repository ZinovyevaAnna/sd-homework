package ru.itmo.simplecli.executor.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {
    @Test
    void test() {
        var env = new EnvironmentManager();
        var cmd = new Assignment(List.of("var", "=", "value"), env);
        cmd.execute(null);
        assertEquals("value", env.get("var"));
    }
}