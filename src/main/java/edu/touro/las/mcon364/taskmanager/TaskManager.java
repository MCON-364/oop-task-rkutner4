package edu.touro.las.mcon364.taskmanager;
/**
 * TaskManager is responsible for dispatching Command objects.
 *
 * <p>This implementation uses JDK 21 pattern-matching switch
 * with a sealed Command hierarchy. Because Command is sealed,
 * the compiler knows all permitted implementations
 * (AddTaskCommand, RemoveTaskCommand, UpdateTaskCommand).
 *
 * <p>This improves:
 * <ul>
 *     <li><b>Readability</b> – Clear, structured dispatch instead of chained instanceof checks.</li>
 *     <li><b>Maintainability</b> – If a new Command type is added,
 *         the compiler forces this switch to be updated.</li>
 *     <li><b>Type safety</b> – Pattern matching automatically casts
 *         the command to its specific type.</li>
 *     <li><b>Exhaustiveness checking</b> – Because Command is sealed,
 *         the switch can verify all cases are handled.</li>
 * </ul>
 */
public class TaskManager {

    private final TaskRegistry registry;

    public TaskManager(TaskRegistry registry) {
        this.registry = registry;
    }

    // refactor this using pattern-matching switch
    public void run(Command command) {
        switch(command) {
            case AddTaskCommand add -> add.execute();
            case RemoveTaskCommand remove -> remove.execute();
            case UpdateTaskCommand update -> update.execute();
            case ChangePriorityCommand c -> c.execute();
        }
    }
}
