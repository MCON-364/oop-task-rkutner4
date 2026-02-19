package edu.touro.las.mcon364.taskmanager;

public final class ChangePriorityCommand implements Command{
    private final TaskRegistry registry;
    private final String taskName;
    private final Priority newPriority;

    public ChangePriorityCommand(TaskRegistry registry, String taskName, Priority newPriority) {
        this.registry = registry;
        this.taskName = taskName;
        this.newPriority = newPriority;
    }

    public void execute() {
        Task task = registry.get(taskName)
                .orElseThrow(() -> new TaskNotFoundException("Task '" + taskName + "' not found"));

        Task updated = new Task(task.name(), newPriority);
        registry.add(updated); // replaces the old task
    }
}
