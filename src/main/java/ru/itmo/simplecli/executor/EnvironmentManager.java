package ru.itmo.simplecli.executor;

import java.util.HashMap;

public class EnvironmentManager {
    private final HashMap<String, String> environment = new HashMap<>();

    public final String get(String name) {
        if (environment.containsKey(name)) {
            return environment.get(name);
        }
        if (System.getenv().containsKey(name)) {
            return System.getenv(name);
        }
        return null;
    }

    public final void set(String name, String value) {
        environment.put(name, value);
    }
}
