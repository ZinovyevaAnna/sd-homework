package ru.itmo.simplecli;

import ru.itmo.simplecli.executor.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnvironmentManager environment = new EnvironmentManager();
        Parser parser = new Parser(environment);

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.trim().equals("")) {
                continue;
            }

            parser.parse(input);
            while (parser.hasUnclosedQuote()) {
                System.out.print("> ");
                input = input + scanner.nextLine();
                parser.parse(input);
            }

            var command = PipedCommandFactory.construct(parser.getResult(), environment);
            if (command == null) {
                System.out.println("Can't construct command");
                continue;
            }

            command.execute(null);
            if (command.getEndStatus() == Executable.EndStatus.EXIT) {
                break;
            }
            System.out.println(command.getOutput());
        }
    }
}
