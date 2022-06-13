package ru.itmo.simplecli.executor.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WCTest {
    private final EnvironmentManager env = new EnvironmentManager();
    private final String path = "src/test/";

    private String callExternal(List<String> args) throws IOException, InterruptedException {
        var process = new ProcessBuilder(args).start();
        process.waitFor();
        return new String(process.getInputStream().readAllBytes());
    }
    private String callExternal(List<String> args, String input) throws IOException, InterruptedException {
        var process = new ProcessBuilder(args).start();
        process.getOutputStream().write(input.getBytes());
        process.getOutputStream().close();
        return new String(process.getInputStream().readAllBytes());
    }

    @Test
    void testFile() {
        var filename = path + "testfile";
        var cmd = new WC(List.of(filename), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        try {
            var expected = callExternal(List.of("wc", filename)).trim().split("\\s+");
            var actual = cmd.getOutput().trim().split("\\s+");
            assertArrayEquals(expected, actual);
        } catch (IOException | InterruptedException e) {
            fail();
        }
    }

    @Test
    void testFiles() {
        var filename = path + "testfile";
        var cmd = new WC(List.of(filename, filename), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmd.getEndStatus());
        try {
            var expected = callExternal(List.of("wc", filename, filename)).trim().split("\\s+");
            var actual = cmd.getOutput().trim().split("\\s+");
            assertArrayEquals(expected, actual);
        } catch (IOException | InterruptedException e) {
            fail();
        }
    }

    @Test
    void testNoFile() {
        var cmd = new WC(new ArrayList<>(), env);
        var input = "test wc";
        cmd.execute(input);
        try {
            var expected = callExternal(List.of("wc", "-"), input).trim().split("\\s+");
            var actual = cmd.getOutput().trim().split("\\s+");
            assertEquals(expected[0], actual[0]);
            assertEquals(expected[1], actual[1]);
            assertEquals(expected[2], actual[2]);
        } catch (IOException | InterruptedException e) {
            fail();
        }
    }

    @Test
    void testNoFileNoInput() {
        var cmd = new WC(new ArrayList<>(), env);
        cmd.execute(null);
        assertEquals(Executable.EndStatus.ERROR, cmd.getEndStatus());
    }
}
