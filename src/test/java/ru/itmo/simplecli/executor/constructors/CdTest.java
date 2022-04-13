package ru.itmo.simplecli.executor.constructors;

import org.junit.jupiter.api.Test;
import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Executable;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdTest {
    @Test
    void test() {
        var env = new EnvironmentManager();
        String path = "src/";
        env.set("PWD", path);
        String newPath = "test";
        String newPathAbs = "src/main";
        var cmdCd = new Cd().construct(List.of(newPath), env);
        var cmdCdAbs = new Cd().construct(List.of(newPathAbs), env);
        var cmdPwd = new Pwd().construct(null, env);
        cmdPwd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmdPwd.getEndStatus());
        assertEquals(path, cmdPwd.getOutput());
        cmdCd.execute(null);
        cmdPwd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmdCd.getEndStatus());
        assertEquals(path + newPath, cmdPwd.getOutput());
        cmdCdAbs.execute(null);
        cmdPwd.execute(null);
        assertEquals(Executable.EndStatus.SUCCESS, cmdCdAbs.getEndStatus());
        assertEquals(newPathAbs, cmdPwd.getOutput());
    }
}
