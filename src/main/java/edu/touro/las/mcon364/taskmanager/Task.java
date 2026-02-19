package edu.touro.las.mcon364.taskmanager;

import java.util.Objects;
//made into a record
public record Task (String name, Priority priority) {
    public Task {
        if (name == null || name.isBlank()) {
            throw new InvalidTaskException("Task name cannot be empty");
        }
        if (priority == null) {
            throw new InvalidTaskException("Task priority cannot be null");
        }
    }
}