package ru.itmo.simplecli.executor.constructors;

import ru.itmo.simplecli.executor.Command;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.io.File;
import java.util.List;

public class Cd implements CommandConstructor {
    @Override
    public Command construct(List<String> args, EnvironmentManager environment) {
        return new Command(args, environment) {
            @Override
            public void execute(String input) {
                if (args.size() > 1) {
                    status = EndStatus.ERROR;
                    output = "Too much arguments for cd command";
                }
                if (args.size() == 0 && input == null) {
                    status = EndStatus.ERROR;
                    output = "Not enough arguments for cd command";
                }
                String newPath = args.get(0);
                File isRealPathAbs = new File(newPath);
                File isRealPath = new File(environment.get("PWD") + "/" + newPath);

                if (isRealPathAbs.exists()) {
                    environment.set("PWD", newPath);
                    status = EndStatus.SUCCESS;
                } else if (isRealPath.exists()) {
                    environment.set("PWD", environment.get("PWD") + "/" + newPath);
                    status = EndStatus.SUCCESS;
                } else {
                    status = EndStatus.ERROR;
                    output = "Wrong path: no such directory";
                }
            }
        };
    }
}
