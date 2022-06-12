package ru.itmo.simplecli.executor.constructors;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PwdTest {

    @Test
    void test() {
        var env = new EnvironmentManager();
        var cmd = new Pwd().construct(new ArrayList<>(), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals(Paths.get("").toAbsolutePath().toString(), cmd.getOutput());
    }

}