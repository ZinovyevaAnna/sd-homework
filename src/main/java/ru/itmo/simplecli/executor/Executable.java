package ru.itmo.simplecli.executor;

/**
 * Class for anything that can be executed
 */
public interface Executable {
    enum EndStatus {
        SUCCESS,
        ERROR,
        EXIT
    }

    /**
     * Executes with the given input
     * @param input
     */
    void execute(String input);

    /**
     * @return result of execution or error message
     */
    String getOutput();

    /**
     * @return status of execution
     */
    EndStatus getEndStatus();
}
