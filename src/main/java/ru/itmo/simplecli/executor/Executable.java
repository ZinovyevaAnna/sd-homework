package ru.itmo.simplecli.executor;

/**
 * Class for anything that can be executed
 */
public interface Executable {
    /**
     * Executes with the given input
     *
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

    enum EndStatus {
        SUCCESS,
        ERROR,
        EXIT
    }
}
