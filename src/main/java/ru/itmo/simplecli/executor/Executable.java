package ru.itmo.simplecli.executor;

public interface Executable {
    enum EndStatus {
        SUCCESS,
        ERROR,
        EXIT;
    }

    void execute(String input);
    String getOutput();
    EndStatus getEndStatus();
}
