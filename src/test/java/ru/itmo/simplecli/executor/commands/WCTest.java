package ru.itmo.simplecli.executor.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WCTest extends AbstractCommandTest {
    private final EnvironmentManager env = new EnvironmentManager();
    private final String path = "src/test/";

    private void assertSpaceInsensitive(String expected, String actual) {
        var value1 = expected.trim().split("\\s+");
        var value2 = actual.trim().split("\\s+");
        assertArrayEquals(value1, value2);
    }

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
        assertSuccess(cmd);
        try {
            assertSpaceInsensitive(callExternal(List.of("wc", filename)), cmd.getOutput());
        } catch (IOException | InterruptedException e) {
            fail();
        }
    }

    @Test
    void testFiles() {
        var filename = path + "testfile";
        var cmd = new WC(List.of(filename, filename), env);
        cmd.execute(null);
        assertSuccess(cmd);
        try {
            assertSpaceInsensitive(callExternal(List.of("wc", filename, filename)), cmd.getOutput());
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
            assertSpaceInsensitive(callExternal(List.of("wc", "-"), input), cmd.getOutput() + "-");
        } catch (IOException | InterruptedException e) {
            fail();
        }
    }

    @Test
    void testNoFileNoInput() {
        var cmd = new WC(List.of(), env);
        cmd.execute(null);
        assertError(cmd);
    }
}
