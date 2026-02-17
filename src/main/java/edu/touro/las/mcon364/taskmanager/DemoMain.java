package edu.touro.las.mcon364.taskmanager;

import java.util.Optional;

public class DemoMain {
    private final TaskRegistry registry;
    private final TaskManager manager;

    public DemoMain() {
        this.registry = new TaskRegistry();
        this.manager = new TaskManager(registry);
    }

    public static void main(String[] args) {
        DemoMain demo = new DemoMain();
        demo.run();
    }

    public void run() {
        System.out.println("=== Task Management System Demo ===\n");

        demonstrateAddingTasks();
        demonstrateRetrievingTask();
        demonstrateUpdatingTask();
        demonstrateUpdatingNonExistentTask();
        demonstrateRemovingTask();
        demonstrateOptionalReturn();
        displaySummary();
    }

    private void demonstrateAddingTasks() {
        System.out.println("1. Adding tasks...");
        manager.run(new AddTaskCommand(registry, new Task("Write documentation", Priority.HIGH)));
        manager.run(new AddTaskCommand(registry, new Task("Review pull requests", Priority.MEDIUM)));
        manager.run(new AddTaskCommand(registry, new Task("Update dependencies", Priority.LOW)));
        manager.run(new AddTaskCommand(registry, new Task("Fix critical bug", Priority.HIGH)));
        manager.run(new AddTaskCommand(registry, new Task("Refactor code", Priority.MEDIUM)));

        System.out.println("   Added 5 tasks to the registry");
        displayAllTasks();
    }

    private void demonstrateRetrievingTask() {
        System.out.println("\n2. Retrieving a specific task...");
        Task retrieved = registry.get("Fix critical bug").orElseThrow();
        System.out.println("   Found: " + retrieved.name() + " (Priority: " + retrieved.priority() + ")");
    }

    private void demonstrateUpdatingTask() {
        System.out.println("\n3. Updating a task's priority...");
        System.out.println("   Changing 'Refactor code' from MEDIUM to HIGH");
        manager.run(new UpdateTaskCommand(registry, "Refactor code", Priority.HIGH));

        Task updated = registry.get("Refactor code").orElseThrow();
        System.out.println("   Updated: " + updated.name() + " (Priority: " + updated.priority() + ")");
    }

    private void demonstrateUpdatingNonExistentTask() {
        System.out.println("\n4. Attempting to update non-existent task...");
        try {
            manager.run(new UpdateTaskCommand(registry, "Non-existent task", Priority.HIGH));
        } catch (TaskNotFoundException e) {
            System.out.println("   Caught TaskNotFoundException: " + e.getMessage());
        }
    }

    private void demonstrateRemovingTask() {
        System.out.println("\n5. Removing a task...");
        manager.run(new RemoveTaskCommand(registry, "Update dependencies"));
        System.out.println("   Removed 'Update dependencies'");
        displayAllTasks();
    }

    private void demonstrateOptionalReturn() {
        System.out.println("\n6. Attempting to retrieve non-existent task...");
        Optional<Task> missing = registry.get("Non-existent task");
        if (missing.isEmpty()) {
            System.out.println("   Returned empty Optional - no task found");
        }
    }

    private void displaySummary() {
        System.out.println("\n=== Demo Complete ===");
        System.out.println("\nNOTE: This code now uses modern Java patterns:");
        System.out.println("  - Task is now a record");
        System.out.println("  - Command interface is sealed");
        System.out.println("  - TaskManager uses pattern-matching switch");
        System.out.println("  - Methods return Optional instead of null");
        System.out.println("  - Custom exception (TaskNotFoundException) is thrown for domain errors");
        System.out.println("  - UpdateTaskCommand no longer silently fails");
    }

    private void displayAllTasks() {
        System.out.println("\n   Current tasks in registry:");
        registry.getAll().forEach((name, task) ->
                System.out.println("     - " + name + " (Priority: " + task.priority() + ")")
        );
    }
}