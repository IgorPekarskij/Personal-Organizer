package interfaces;

import objects.Task;

public interface ITask {
    static int addTask (Task task){return -1;}

    static void updateTask (Task task){}

    static void deleteTask (Task task){}
}
