package ru.itmo.simplecli.executor;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.constructors.*;

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
                new Cat().construct(args.subList(1, args.size()), env).getClass());


        args = List.of("echo");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                new Echo().construct(args.subList(1, args.size()), env).getClass());


        args = List.of("exit");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                new Exit().construct(args.subList(1, args.size()), env).getClass());


        args = List.of("grep");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                new Grep().construct(args.subList(1, args.size()), env).getClass());


        args = List.of("pwd");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                new Pwd().construct(args.subList(1, args.size()), env).getClass());


        args = List.of("wc");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                new WC().construct(args.subList(1, args.size()), env).getClass());


        args = List.of("unknowncommand");
        assertEquals(Objects.requireNonNull(CommandFactory.construct(args, env)).getClass(),
                new External().construct(args.subList(1, args.size()), env).getClass());

        assertNull(CommandFactory.construct(new ArrayList<>(), env));
    }

}