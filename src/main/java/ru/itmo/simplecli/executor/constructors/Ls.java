package ru.itmo.simplecli.executor.constructors;

import ru.itmo.simplecli.executor.Command;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Returns a content of current directory in alphabetical order
 */
public class Ls implements CommandConstructor {
    @Override
    public Command construct(List<String> args, EnvironmentManager environment) {
        return new Command(args, environment) {
            @Override
            public void execute(String input) {
                String pathToCheck = environment.get("PWD");
                if (pathToCheck == null) {
                    status = EndStatus.ERROR;
                    output = "No current directory";
                } else {
                    List<String> fileList = new ArrayList<>();

                    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pathToCheck))) {
                        for (Path path : stream) {
                            fileList.add(path.getFileName().toString());
                        }
                    } catch (IOException e) {
                        status = EndStatus.ERROR;
                        output = e.getMessage();
                    }
                    java.util.Collections.sort(fileList);
                    status = EndStatus.SUCCESS;
                    output = String.join("\n", fileList);
                }
            }
        };
    }
}
