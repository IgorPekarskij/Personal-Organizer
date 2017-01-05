package interfaces;

import objects.Task;

public interface ITask {
    static void addTask (Task task){};
    void updateTask (Task task);
    static void deleteTask (Task task){};
}
