package ru.itmo.simplecli.executor.commands;

import ru.itmo.simplecli.executor.EnvironmentManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Counts number of lines, symbols and bytes for every given file
 */
public class WC extends Command {

    public WC(List<String> args, EnvironmentManager environment) {
        super(args, environment);
    }

    @Override
    public void execute(String input) {
        if (args.size() == 0 && input == null) {
            status = EndStatus.ERROR;
            output = "Not enough arguments for `wc` command";
            return;
        }
        try {
            if (args.size() == 0) {
                var reader = new BufferedReader(new StringReader(input));
                output = handle(reader, "").toString();
            } else {
                int totalLines = 0, totalWords = 0, totalBytes = 0;
                var builder = new StringBuilder();
                for (var arg : args) {
                    var file = new BufferedReader(new FileReader(arg));
                    var result = handle(file, arg);
                    totalLines += result.lines;
                    totalWords += result.words;
                    totalBytes += result.bytes;
                    builder.append(result);
                }
                if (args.size() > 1) {
                    builder.append(new ResultLine(totalLines, totalWords, totalBytes, "total"));
                }
                output = builder.toString();
            }
            status = EndStatus.SUCCESS;
        } catch (IOException e) {
            status = EndStatus.ERROR;
            output = e.getMessage();
        }
    }

    private ResultLine handle(BufferedReader file, String name) throws IOException {
        int lines = 0, words = 0, bytes = 0;
        int prev = ' ', c;
        while ((c = file.read()) != -1) {
            bytes += 1;
            if ((char) c == '\n') {
                lines += 1;
            }
            if (Character.isWhitespace(prev) && !Character.isWhitespace(c)) {
                words += 1;
            }
            prev = c;
        }
        return new ResultLine(lines, words, bytes, name);
    }

    private record ResultLine(int lines, int words, int bytes, String name) {
        public String toString() {
            return lines + "\t" +
                words + "\t" +
                bytes + "\t" +
                name +
                "\n";
        }
    }
}
