package ru.itmo.simplecli.executor.constructors;

import ru.itmo.simplecli.executor.EnvironmentManager;
import ru.itmo.simplecli.executor.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class for not build-ins command
 */
public class External implements CommandConstructor {
    @Override
    public Command construct(List<String> args, EnvironmentManager environment) {
        return new Command(args, environment) {
            @Override
            public void execute(String input) {
                try {
                    var process = new ProcessBuilder(args).start();
                    if (input != null) {
                        var stream = process.getOutputStream();
                        stream.write(input.getBytes(StandardCharsets.UTF_8));
                        stream.close();
                    }
                    var exitCode = process.waitFor();
                    var sb = new StringBuilder();
                    var stream = exitCode == 0 ? process.getInputStream() : process.getErrorStream();
                    var reader = new BufferedReader(new InputStreamReader(stream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    output = sb.toString();
                    status = exitCode == 0 ? EndStatus.SUCCESS : EndStatus.ERROR;
                } catch (IOException | InterruptedException e) {
                    status = EndStatus.ERROR;
                    output = e.getMessage();
                }
            }
        };
    }
}
