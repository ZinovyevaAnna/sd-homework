package ru.itmo.simplecli.executor.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentTest extends AbstractCommandTest {
    @Test
    void test() {
        var env = new EnvironmentManager();
        var cmd = new Assignment(List.of("var", "=", "value"), env);
        cmd.execute(null);
        assertSuccess(cmd);
        assertEquals("value", env.get("var"));
    }
}