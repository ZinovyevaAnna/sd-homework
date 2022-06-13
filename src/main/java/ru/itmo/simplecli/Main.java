package ru.itmo.simplecli;

import ru.itmo.simplecli.executor.*;

import java.io.PrintStream;
import java.util.*;

/**
 * Entry point. Loop: read - parse - execute.
 */
public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final PrintStream output = new PrintStream(System.out);
    public static void main(String[] args) {
        EnvironmentManager environment = new EnvironmentManager();
        Parser parser = new Parser(environment);

        while (true) {
            output.print(">> ");
            String nextLine = Main.input.nextLine();
            if (nextLine.trim().equals("")) {
                continue;
            }

            parser.parse(nextLine);
            while (parser.hasUnclosedQuote()) {
                output.print("> ");
                nextLine = nextLine + Main.input.nextLine();
                parser.parse(nextLine);
            }

            var command = PipedCommandFactory.construct(parser.getResult(), environment);
            if (command == null) {
                output.println("Can't construct command");
                continue;
            }

            command.execute(null);
            if (command.getEndStatus() == Executable.EndStatus.EXIT) {
                break;
            }
            if (command.getOutput() != null) {
                output.println(command.getOutput());
            }
        }
    }
}
