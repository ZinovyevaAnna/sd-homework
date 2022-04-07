package ru.itmo.simplecli.executor.constructors;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WCTest {
    private final EnvironmentManager env = new EnvironmentManager();
    private final String path = "src/test/";

    @Test
    void testFile() {
        var filename = path + "testfile";
        var cmd = new WC().construct(List.of(filename), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("1\t2\t9\t" + filename, cmd.getOutput().trim());
    }

    @Test
    void testFiles() {
        var filename = path + "testfile";
        var cmd = new WC().construct(List.of(filename, filename), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("1\t2\t9\t" + filename + "\n1\t2\t9\t" + filename, cmd.getOutput().trim());
    }

    @Test
    void testNoFile() {
        var cmd = new WC().construct(new ArrayList<>(), env);
        cmd.execute("test wc");
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        assertEquals("1\t2\t7", cmd.getOutput().trim());
    }

    @Test
    void testNoFileNoInput() {
        var cmd = new WC().construct(new ArrayList<>(), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.ERROR, cmd.getEndStatus());
    }
}
