package ru.itmo.simplecli.executor.constructors;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrepTest {
    private final EnvironmentManager env = new EnvironmentManager();
    private final String path = "src/test/";

    @Test
    void testFile() {
        var cmd = new Grep().construct(List.of("test", path + "testfile"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test file", cmd.getOutput().trim());

        cmd = new Grep().construct(List.of("no test", path + "testfile"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("", cmd.getOutput().trim());
    }

    @Test
    void testFiles() {
        var cmd = new Grep().construct(List.of("test", path + "testfile", path + "testfile"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test file\ntest file", cmd.getOutput().trim());
    }

    @Test
    void testNoFile() {
        var cmd = new Grep().construct(List.of("test"), env);
        cmd.execute("test grep");
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test grep", cmd.getOutput().trim());

        cmd = new Grep().construct(List.of("no test"), env);
        cmd.execute("test grep");
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("", cmd.getOutput().trim());
    }

    @Test
    void testNoFileNoInput() {
        var cmd = new Grep().construct(new ArrayList<>(), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.ERROR, cmd.getEndStatus());

        cmd = new Grep().construct(List.of("test"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.ERROR, cmd.getEndStatus());
    }

    @Test
    void testOptions() {
        var cmd = new Grep().construct(List.of("-i", "TEST", path + "testfile"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test file", cmd.getOutput().trim());

        cmd = new Grep().construct(List.of("-w", "test", path + "testfile"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test file", cmd.getOutput().trim());

        cmd = new Grep().construct(List.of("-w", "t", path + "testfile"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("", cmd.getOutput().trim());

        cmd = new Grep().construct(List.of("-A", "2", "test", path + "testfile2"), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("test\nfile", cmd.getOutput().trim());
    }
}