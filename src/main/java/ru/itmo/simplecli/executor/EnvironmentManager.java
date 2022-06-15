package ru.itmo.simplecli.executor;

import java.util.HashMap;

/**
 * Class for local environment
 */
public class EnvironmentManager {
    private final HashMap<String, String> environment = new HashMap<>();

    /**
     * If there is no local variable uses global environment
     *
     * @param name name of the variable
     * @return value of the variable
     */
    public final String get(String name) {
        if (environment.containsKey(name)) {
            return environment.get(name);
        }
        if (System.getenv().containsKey(name)) {
            return System.getenv(name);
        }
        return "";
    }

    /**
     * Set or rewrites variable value
     *
     * @param name  name of a variable
     * @param value value of a variable
     */
    public final void set(String name, String value) {
        environment.put(name, value);
    }
}
