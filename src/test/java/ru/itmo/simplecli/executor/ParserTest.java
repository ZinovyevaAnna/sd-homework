package ru.itmo.simplecli.executor;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    private final EnvironmentManager env = new EnvironmentManager();
    private final Parser parser = new Parser(env);

    @Test
    public void testLonelyCommand() {
        parser.parse("cat file1 file2");
        assertEquals(parser.getResult(), List.of("cat", "file1", "file2"));

        parser.parse("echo input");
        assertEquals(parser.getResult(), List.of("echo", "input"));

        parser.parse("exit");
        assertEquals(parser.getResult(), List.of("exit"));

        parser.parse("grep -i -w -A 10 pattern file1 file2");
        assertEquals(parser.getResult(),
                List.of("grep", "-i", "-w", "-A", "10", "pattern", "file1", "file2"));

        parser.parse("pwd");
        assertEquals(parser.getResult(), List.of("pwd"));

        parser.parse("wc file1 file2");
        assertEquals(parser.getResult(), List.of("wc", "file1", "file2"));
    }

    @Test
    public void testExtraSpaces() {
        parser.parse("cmd     arg1     arg2");
        assertEquals(parser.getResult(), List.of("cmd", "arg1", "arg2"));
    }

    @Test
    public void testDoubleQuoted() {
        parser.parse("cmd \"arg1\" \" arg2\" ");
        assertEquals(parser.getResult(), List.of("cmd", "arg1", " arg2"));

        parser.parse("cmd \"arg");
        assertTrue(parser.hasUnclosedQuote());

        parser.parse(" \"$PWD\" ");
        assertEquals(parser.getResult(), List.of(Objects.requireNonNull(env.get("PWD"))));
    }

    @Test
    public void testSingleQuoted() {
        parser.parse("cmd 'arg1' ' arg2' ");
        assertEquals(parser.getResult(), List.of("cmd", "arg1", " arg2"));

        parser.parse("cmd 'arg");
        assertTrue(parser.hasUnclosedQuote());

        parser.parse(" '$PWD' ");
        assertEquals(parser.getResult(), List.of("$PWD"));
    }

    @Test
    public void testPiped() {
        parser.parse(" cmd1 arg  |  cmd2 arg |  cmd3 arg ");
        assertEquals(parser.getResult(),
                List.of("cmd1", "arg", "|", "cmd2", "arg", "|", "cmd3", "arg"));
    }
}