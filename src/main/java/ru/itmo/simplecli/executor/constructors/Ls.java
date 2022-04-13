package ru.itmo.simplecli.executor.constructors;

import ru.itmo.simplecli.executor.Command;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Returns a content of directory in alphabetical order
 */
public class Ls implements CommandConstructor {
    @Override
    public Command construct(List<String> args, EnvironmentManager environment) {
        return new Command(args, environment) {
            @Override
            public void execute(String input) {
                if (args.size() > 1) {
                    status = EndStatus.ERROR;
                    output = "Too much arguments for ls command";
                    return;
                }
                String pathToCheck = environment.get("PWD");
                if (args.size() == 1) {
                    File isRealPath = new File(args.get(0));
                    if (!isRealPath.exists()) {
                        File isRealPathAbs = new File(environment.get("PWD") + "/" + args.get(0));
                        if (isRealPathAbs.exists()) {
                            pathToCheck = isRealPathAbs.toString();
                        } else {
                            status = EndStatus.ERROR;
                            output = "No such file or directory";
                            return;
                        }
                    } else {
                        pathToCheck = isRealPath.toString();
                    }
                }

                if (pathToCheck == null) {
                    status = EndStatus.ERROR;
                    output = "No current directory";
                } else {
                    List<String> fileList = new ArrayList<>();
                    List<String> directoriesList = new ArrayList<>();
                    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pathToCheck))) {
                        for (Path path : stream) {
                            String fileName = path.getFileName().toString();
                            if (!fileName.startsWith(".")) {
                                if (path.toFile().isFile()) {
                                    fileList.add(fileName);
                                } else {
                                    directoriesList.add(fileName);
                                }

                            }
                        }
                    } catch (IOException e) {
                        status = EndStatus.ERROR;
                        output = e.getMessage();
                        return;
                    }
                    java.util.Collections.sort(directoriesList);
                    java.util.Collections.sort(fileList);
                    directoriesList.addAll(fileList);
                    status = EndStatus.SUCCESS;
                    output = String.join("\n", directoriesList);
                }
            }
        };
    }
}
