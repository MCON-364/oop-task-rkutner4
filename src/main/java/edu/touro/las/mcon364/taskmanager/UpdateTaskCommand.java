package edu.touro.las.mcon364.taskmanager;

//made this final
public final class UpdateTaskCommand implements Command {
    private final TaskRegistry registry;
    private final String taskName;
    private final Priority newPriority;

    public UpdateTaskCommand(TaskRegistry registry, String taskName, Priority newPriority) {
        this.registry = registry;
        this.taskName = taskName;
        this.newPriority = newPriority;
    }

    public void execute() {
        //changed to use custom exception and or else throw
        Task existing = registry.get(taskName)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task '" + taskName + "' not found"));

        // Create a new task with updated priority (tasks are immutable)
        Task updated = new Task(existing.name(), newPriority);
        registry.add(updated);  // This replaces the old task
    }
}
