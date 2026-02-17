package edu.touro.las.mcon364.taskmanager;

//made this sealed and added what is allowed to implement it
public sealed interface Command
        permits AddTaskCommand, RemoveTaskCommand, UpdateTaskCommand {
    void execute();
}
