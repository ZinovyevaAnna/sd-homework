package ru.itmo.simplecli.executor.constructors;

import ru.itmo.simplecli.executor.Command;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Changes current directory
 */
public class Cd implements CommandConstructor {
    @Override
    public Command construct(List<String> args, EnvironmentManager environment) {
        return new Command(args, environment) {
            @Override
            public void execute(String input) {
                if (args.size() > 1) {
                    status = EndStatus.ERROR;
                    output = "Too much arguments for cd command";
                    return;
                }
                if (args.size() == 0 && input == null) {
                    status = EndStatus.ERROR;
                    output = "Not enough arguments for cd command";
                    return;
                }
                String newPath = args.get(0);
                File isRealPathAbs = new File(newPath);
                File isRealPath;
                if (Objects.requireNonNull(environment.get("PWD")).endsWith("/")) {
                    isRealPath = new File(environment.get("PWD") + newPath);
                } else {
                    isRealPath = new File(environment.get("PWD") + "/" + newPath);
                }

                if (isRealPathAbs.exists()) {
                    environment.set("PWD", isRealPathAbs.toString());
                    status = EndStatus.SUCCESS;
                } else if (isRealPath.exists()) {
                    environment.set("PWD", isRealPath.toString());
                    status = EndStatus.SUCCESS;
                } else {
                    status = EndStatus.ERROR;
                    output = "Wrong path: no such directory";
                }
            }
        };
    }
}
