package ru.itmo.simplecli.executor.commands;

import ru.itmo.simplecli.executor.EnvironmentManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Concatenates given files content
 */
public class Cat extends Command {


    public Cat(List<String> args, EnvironmentManager environment) {
        super(args, environment);
    }

    @Override
    public void execute(String input) {
        if (args.size() == 0 && input == null) {
            status = EndStatus.ERROR;
            output = "Not enough arguments for `cat` command";
            return;
        }

        if (args.size() == 0) {
            output = handle(new BufferedReader(new StringReader(input)));
            status = EndStatus.SUCCESS;
            return;
        }

        try {
            var sb = new StringBuilder();
            for (var arg : args) {
                var file = new BufferedReader(new FileReader(arg));
                sb.append(handle(file));
            }
            output = sb.toString();
            status = EndStatus.SUCCESS;
        } catch (IOException e) {
            status = EndStatus.ERROR;
            output = e.getMessage();
        }

    }

    private String handle(BufferedReader file) {
        var sb = new StringBuilder();
        for (var line : file.lines().toList()) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

}