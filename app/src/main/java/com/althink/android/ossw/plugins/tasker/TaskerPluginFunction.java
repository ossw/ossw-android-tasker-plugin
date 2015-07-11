package com.althink.android.ossw.plugins.tasker;

/**
 * Created by krzysiek on 14/06/15.
 */
public enum TaskerPluginFunction {

    INVOKE_TASK(1, "invokeTask");

    private final int id;
    private final String name;

    private TaskerPluginFunction(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static TaskerPluginFunction resolveById(int functionId) {
        for (TaskerPluginFunction function : TaskerPluginFunction.values()) {
            if (function.getId() == functionId) {
                return function;
            }
        }
        return null;
    }
}
