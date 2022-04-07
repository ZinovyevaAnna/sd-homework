package ru.itmo.simplecli.executor.constructors;

import org.apache.commons.cli.*;
import ru.itmo.simplecli.executor.Command;
import ru.itmo.simplecli.executor.EnvironmentManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Searches pattern in given files
 */
public class Grep implements CommandConstructor {
    @Override
    public Command construct(List<String> args, EnvironmentManager environment) {
        return new Command(args, environment) {
            private int N_AFTER;
            private String expression;
            private List<String> files;
            private Pattern pattern;

            @Override
            public void execute(String input) {
                try {
                    parseOptions();
                    var sb = new StringBuilder();
                    pattern = Pattern.compile(expression);
                    if (!files.isEmpty()) {
                        for (var file : files) {
                            sb.append(handle(new BufferedReader(new FileReader(file))));
                        }
                    } else if (input != null) {
                        sb.append(handle(new BufferedReader(new StringReader(input))));
                    } else {
                        status = EndStatus.ERROR;
                        output = "Not enough arguments for `grep` command";
                        return;
                    }
                    output = sb.toString();
                    status = EndStatus.SUCCESS;
                } catch (ParseException | NumberFormatException | FileNotFoundException e) {
                    status = EndStatus.ERROR;
                    output = e.getMessage();
                } catch (IndexOutOfBoundsException e) {
                    status = EndStatus.ERROR;
                    output = "No pattern was found in `grep` command arguments";
                }
            }

            private void parseOptions() throws ParseException, NumberFormatException, IndexOutOfBoundsException {
                var optionI = Option.builder("i").build();
                var optionW = Option.builder("w").build();
                var optionA = Option.builder("A").hasArg().build();
                var options = new Options().addOption(optionI).addOption(optionA).addOption(optionW);
                var rawArgs = new String[args.size()];
                args.toArray(rawArgs);
                var cmd = new DefaultParser().parse(options, rawArgs);
                N_AFTER = cmd.hasOption("A") ? Integer.parseInt(cmd.getOptionValue("A")) : 1;
                var left = Arrays.stream(cmd.getArgs()).toList();
                expression = left.get(0);
                if (cmd.hasOption("w")) {
                    expression = "(^|\\W)" + expression + "(\\W|$)";
                }
                expression = (cmd.hasOption("i") ? "(?i)" : "") + ".*" + expression + ".*";
                files = new ArrayList<>(left.subList(1, left.size()));
            }

            private String handle(BufferedReader reader) {
                var getLines = 0;
                var sb = new StringBuilder();
                for (var line : reader.lines().toList()) {
                    if (pattern.matcher(line).matches()) {
                        getLines = N_AFTER;
                    }
                    if (getLines > 0) {
                        sb.append(line).append("\n");
                        getLines -= 1;
                    }
                }
                return sb.toString();
            }
        };
    }
}