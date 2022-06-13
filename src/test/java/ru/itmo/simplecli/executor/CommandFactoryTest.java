package ru.itmo.simplecli.executor;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandFactoryTest {
    public EnvironmentManager env = new EnvironmentManager();
    @Test
    void testImplemented() {
        var args = List.of("cat");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                Cat.class);


        args = List.of("echo");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                Echo.class);


        args = List.of("exit");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                Exit.class);


        args = List.of("pwd");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                Pwd.class);


        args = List.of("wc");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                WC.class);


        args = List.of("unknowncommand");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                External.class);

        assertNull(CommandFactory.construct(new ArrayList<>(), env));
    }

}