package ru.itmo.simplecli.executor;

import java.util.List;

/**
 * Piped command executes every command, pass output of one as input to the next one
 */
class PipedCommand implements Executable {
    private EndStatus status;
    private String output;
    private final List<Executable> executables;

    public PipedCommand(List<Executable> executables) {
        this.executables = executables;
    }

    @Override
    public void execute(String input) {
        output = input;
        for (var executable : executables) {
            executable.execute(output);
            output = executable.getOutput();
            status = executable.getEndStatus();
            if (status != EndStatus.SUCCESS) {
                return;
            }
        }
    }

    @Override
    public String getOutput() {
        return output;
    }

    @Override
    public EndStatus getEndStatus() {
        return status;
    }
}
