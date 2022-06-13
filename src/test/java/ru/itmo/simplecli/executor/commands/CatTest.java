package ru.itmo.simplecli.executor.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class CatTest extends AbstractCommandTest {
    private final EnvironmentManager env = new EnvironmentManager();
    private final String path = "src/test/";
    @Test
    void testFile() {
        var cmd = new Cat(List.of(path + "testfile"), env);
        cmd.execute(null);
        assertSuccess(cmd);
        assertEquals("test file", cmd.getOutput().trim());
    }

    @Test
    void testFile2() {
        var cmd = new Cat(List.of(path + "testfile2"), env);
        cmd.execute(null);
        assertSuccess(cmd);
        assertEquals("test\nfile", cmd.getOutput().trim());
    }

    @Test
    void testFiles() {
        var cmd = new Cat(List.of(path + "testfile", path + "testfile"), env);
        cmd.execute(null);
        assertSuccess(cmd);
        assertEquals("test file\ntest file", cmd.getOutput().trim());
    }


    void testNoFile() {
        var cmd = new Cat(new ArrayList<>(), env);
        cmd.execute("test cat");
        assertSuccess(cmd);
        assertEquals("test cat", cmd.getOutput().trim());
    }

    @Test
    void testNoFileNoInput() {
        var cmd = new Cat(new ArrayList<>(), env);
        cmd.execute(null);
        assertError(cmd);
    }
}