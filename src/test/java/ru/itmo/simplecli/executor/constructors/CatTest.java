package ru.itmo.simplecli.executor.constructors;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CatTest {
    private final EnvironmentManager env = new EnvironmentManager();
    private final String path = "src/test/";
    @Test
    void testFile() {
        var cmd = new Cat().construct(List.of(path + "testfile"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test file", cmd.getOutput().trim());

        cmd = new Cat().construct(List.of(path + "testfile2"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test\nfile", cmd.getOutput().trim());
    }

    @Test
    void testFiles() {
        var cmd = new Cat().construct(List.of(path + "testfile", path + "testfile"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test file\ntest file", cmd.getOutput().trim());
    }

    @Test
    void testNoFile() {
        var cmd = new Cat().construct(new ArrayList<>(), env);
        cmd.execute("test cat");
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test cat", cmd.getOutput().trim());
    }

    @Test
    void testNoFileNoInput() {
        var cmd = new Cat().construct(new ArrayList<>(), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.ERROR, cmd.getEndStatus());
    }
}