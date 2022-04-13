package ru.itmo.simplecli.executor;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple parser that handles single and double quotes
 */
public class Parser {
    private final EnvironmentManager environment;
    private List<Wrapped> result;
    private State state;

    public Parser(EnvironmentManager environment) {
        this.environment = environment;
    }

    /**
     * @param input string that contains command with args
     */
    public void parse(String input) {
        result = new ArrayList<>();
        state = State.NO_QUOTED;
        runParse(input);
    }

    /**
     * @return result of parsing
     */
    public List<List<String>> getResult() {
        var processed = result.stream().map(Wrapped::getValue).toList();
        List<List<String>> piped = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (var element : processed) {
            if (element.equals("|")) {
                piped.add(current);
                current = new ArrayList<>();
            } else {
                current.add(element);
            }
        }
        piped.add(current);
        return piped;
    }

    /**
     * @return value if input string had proper quotes
     */
    public boolean hasUnclosedQuote() {
        return state != State.NO_QUOTED;
    }

    private class Wrapped {
        protected StringBuilder value;
        Wrapped(StringBuilder value) {
            this.value = value;
        }
        public String getValue() {
            if (value.indexOf("$") != -1) {
                var start = value.indexOf("$");
                var end = start + 1;
                while (end < value.length()) {
                    var symbol = value.charAt(end);
                    if (!Character.isAlphabetic(symbol)
                    && !Character.isDigit(symbol)
                    && !(symbol == '_')) {
                            break;
                    }
                    end++;
                }
                var varName = value.substring(start + 1, end);
                value.delete(start, end);
                value.insert(start, environment.get(varName));
            }
            return value.toString();
        }
    }

    private class SingleQuoted extends Wrapped {
        SingleQuoted(StringBuilder value) {
            super(value);
        }
        @Override
        public String getValue() {
            return value.toString();
        }
    }

    private class DoubleQuoted extends Wrapped {
        DoubleQuoted(StringBuilder value) {
            super(value);
        }
    }

    private void runParse(String string) {
        StringBuilder current = new StringBuilder();
        for (int pos = 0; pos < string.length(); pos++) {
            var symbol = string.charAt(pos);

            if (state == State.SINGLE_QUOTED) {
                if (symbol == '\'') {
                    result.add(new SingleQuoted(current));
                    current = new StringBuilder();
                    state = State.NO_QUOTED;
                } else {
                    current.append(symbol);
                }
            } else if (state == State.DOUBLE_QUOTED) {
                if (symbol == '"') {
                    result.add(new DoubleQuoted(current));
                    current = new StringBuilder();
                    state = State.NO_QUOTED;
                } else {
                    current.append(symbol);
                }
            } else {
                if (symbol == ' ') {
                    if (!current.isEmpty()) {
                        result.add(new Wrapped(current));
                        current = new StringBuilder();
                    }
                } else if (symbol == '|' || symbol == '=') {
                    if (!current.isEmpty()) {
                        result.add(new Wrapped(current));
                        current = new StringBuilder();
                    }
                    result.add(new Wrapped(new StringBuilder().append(symbol)));
                } else if (symbol == '\'') {
                    if (!current.isEmpty()) {
                        result.add(new Wrapped(current));
                        current = new StringBuilder();
                    }
                    state = State.SINGLE_QUOTED;
                } else if (symbol == '"') {
                    if (!current.isEmpty()) {
                        result.add(new Wrapped(current));
                        current = new StringBuilder();
                    }
                    state = State.DOUBLE_QUOTED;
                } else {
                    current.append(symbol);
                }
            }
        }
        if (state == State.NO_QUOTED && !current.isEmpty()) {
            result.add(new Wrapped(current));
        }
    }

    private enum State {
        NO_QUOTED,
        DOUBLE_QUOTED,
        SINGLE_QUOTED
    }
}
