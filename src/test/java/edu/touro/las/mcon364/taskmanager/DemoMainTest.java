package edu.touro.las.mcon364.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DemoMain to help students verify behavior is preserved during refactoring.
 * Each test validates one of the demonstration methods.
 */
class DemoMainTest {
    private DemoMain demo;
    private TaskRegistry registry;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        demo = new DemoMain();
        registry = new TaskRegistry();

        // Capture System.out for output verification
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("Adding tasks should create 5 tasks with correct priorities")
    void testDemonstrateAddingTasks() {
        TaskRegistry testRegistry = new TaskRegistry();
        TaskManager testManager = new TaskManager(testRegistry);

        testManager.run(new AddTaskCommand(testRegistry, new Task("Write documentation", Priority.HIGH)));
        testManager.run(new AddTaskCommand(testRegistry, new Task("Review pull requests", Priority.MEDIUM)));
        testManager.run(new AddTaskCommand(testRegistry, new Task("Update dependencies", Priority.LOW)));
        testManager.run(new AddTaskCommand(testRegistry, new Task("Fix critical bug", Priority.HIGH)));
        testManager.run(new AddTaskCommand(testRegistry, new Task("Refactor code", Priority.MEDIUM)));

        assertEquals(5, testRegistry.getAll().size(), "Should have 5 tasks");

        Task doc = testRegistry.get("Write documentation").orElseThrow();
        assertEquals(Priority.HIGH, doc.priority(), "Write documentation should be HIGH priority");

        Task review = testRegistry.get("Review pull requests").orElseThrow();
        assertEquals(Priority.MEDIUM, review.priority(), "Review pull requests should be MEDIUM priority");

        Task dependencies = testRegistry.get("Update dependencies").orElseThrow();
        assertEquals(Priority.LOW, dependencies.priority(), "Update dependencies should be LOW priority");

        Task bug = testRegistry.get("Fix critical bug").orElseThrow();
        assertEquals(Priority.HIGH, bug.priority(), "Fix critical bug should be HIGH priority");

        Task refactor = testRegistry.get("Refactor code").orElseThrow();
        assertEquals(Priority.MEDIUM, refactor.priority(), "Refactor code should be MEDIUM priority");
    }

    @Test
    @DisplayName("Retrieving existing task should return correct task")
    void testDemonstrateRetrievingTask() {
        TaskRegistry testRegistry = new TaskRegistry();
        TaskManager testManager = new TaskManager(testRegistry);

        Task expectedTask = new Task("Fix critical bug", Priority.HIGH);
        testManager.run(new AddTaskCommand(testRegistry, expectedTask));

        Task retrieved = testRegistry.get("Fix critical bug").orElseThrow();

        assertEquals("Fix critical bug", retrieved.name(), "Task name should match");
        assertEquals(Priority.HIGH, retrieved.priority(), "Task priority should match");
        assertEquals(expectedTask, retrieved, "Retrieved task should equal the added task");
    }

    @Test
    @DisplayName("Updating task should change priority")
    void testDemonstrateUpdatingTask() {
        TaskRegistry testRegistry = new TaskRegistry();
        TaskManager testManager = new TaskManager(testRegistry);

        testManager.run(new AddTaskCommand(testRegistry, new Task("Refactor code", Priority.MEDIUM)));

        Task before = testRegistry.get("Refactor code").orElseThrow();
        assertEquals(Priority.MEDIUM, before.priority(), "Initial priority should be MEDIUM");

        testManager.run(new UpdateTaskCommand(testRegistry, "Refactor code", Priority.HIGH));

        Task after = testRegistry.get("Refactor code").orElseThrow();
        assertEquals(Priority.HIGH, after.priority(), "Priority should be updated to HIGH");
        assertEquals("Refactor code", after.name(), "Task name should remain unchanged");
    }

    @Test
    @DisplayName("Removing task should delete it from registry")
    void testDemonstrateRemovingTask() {
        TaskRegistry testRegistry = new TaskRegistry();
        TaskManager testManager = new TaskManager(testRegistry);

        testManager.run(new AddTaskCommand(testRegistry, new Task("Update dependencies", Priority.LOW)));
        testManager.run(new AddTaskCommand(testRegistry, new Task("Fix critical bug", Priority.HIGH)));

        assertEquals(2, testRegistry.getAll().size(), "Should have 2 tasks initially");

        testManager.run(new RemoveTaskCommand(testRegistry, "Update dependencies"));

        assertEquals(1, testRegistry.getAll().size(), "Should have 1 task after removal");
        assertTrue(testRegistry.get("Fix critical bug").isPresent(), "Fix critical bug should still exist");
        assertTrue(testRegistry.get("Update dependencies").isEmpty(), "Update dependencies should be removed");
    }

    @Test
    @DisplayName("Full demo run should execute without exceptions")
    void testFullDemoRun() {
        DemoMain testDemo = new DemoMain();
        assertDoesNotThrow(testDemo::run, "Full demo should run without exceptions");
    }
}