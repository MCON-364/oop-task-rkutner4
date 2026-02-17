package edu.touro.las.mcon364.taskmanager;

/**
 * Thrown when a task cannot be found in the registry.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
