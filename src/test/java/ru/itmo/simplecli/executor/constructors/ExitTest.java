package ru.itmo.simplecli.executor.constructors;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExitTest {

    @Test
    void test() {
        var env = new EnvironmentManager();
        var cmd = new Exit().construct(new ArrayList<>(), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.EXIT, cmd.getEndStatus());
        assertNull(cmd.getOutput());
    }

}