package ru.itmo.simplecli.executor.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdTest extends AbstractCommandTest {

    @Test
    void test() {
        var env = new EnvironmentManager();
        var cmd = new Pwd(new ArrayList<>(), env);
        cmd.execute(null);
        assertSuccess(cmd);
        assertEquals(Paths.get("").toAbsolutePath().toString(), cmd.getOutput());
    }

}