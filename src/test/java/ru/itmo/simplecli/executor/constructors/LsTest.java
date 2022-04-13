package ru.itmo.simplecli.executor.constructors;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsTest {
    @Test
    void test() {
        var env = new EnvironmentManager();
        String path = "src/test/";
        env.set("PWD", path);
        String pathAbs = "src/main/java/ru/itmo/simplecli/";
        var cmdWithoutPath = new Ls().construct(List.of(path), env);
        var cmdWithPath = new Ls().construct(List.of(pathAbs), env);
        cmdWithoutPath.execute(null);
        cmdWithPath.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmdWithoutPath.getEndStatus());
        assertEquals("java\ntestfile\ntestfile2", cmdWithoutPath.getOutput());
        assertEquals(Executable.EndStatus.SUCCESS, cmdWithPath.getEndStatus());
        assertEquals("executor\nMain.java", cmdWithPath.getOutput());
    }
}
