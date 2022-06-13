package ru.itmo.simplecli.executor;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SingleCommandFactoryTest {
    private final AbstractCommandFactory instance = new SingleCommandFactory();
    public EnvironmentManager env = new EnvironmentManager();

    @Test
    public void testCatImplemented() {
        var args = List.of("cat");
        assertEquals(Objects.requireNonNull(instance.construct(args, env)).getClass(),
            Cat.class);
    }

    @Test
    public void testEchoImplemented() {
        var args = List.of("echo");
        assertEquals(Objects.requireNonNull(instance.construct(args, env)).getClass(),
            Echo.class);
    }

    @Test
    public void testExitImplemented() {
        var args = List.of("exit");
        assertEquals(Objects.requireNonNull(instance.construct(args, env)).getClass(),
            Exit.class);
    }

    @Test
    public void testPwdImplemented() {
        var args = List.of("pwd");
        assertEquals(Objects.requireNonNull(instance.construct(args, env)).getClass(),
            Pwd.class);
    }

    @Test
    public void testWCImplemented() {
        var args = List.of("wc");
        assertEquals(Objects.requireNonNull(instance.construct(args, env)).getClass(),
            WC.class);
    }

    @Test
    public void testExternalImplemented() {
        var args = List.of("unknowncommand");
        assertEquals(Objects.requireNonNull(instance.construct(args, env)).getClass(),
            External.class);

        assertNull(instance.construct(new ArrayList<>(), env));
    }

    @Test
    public void testConstructorWithEmptyInput() {
        assertNull(instance.construct(new ArrayList<>(), env));
    }
}