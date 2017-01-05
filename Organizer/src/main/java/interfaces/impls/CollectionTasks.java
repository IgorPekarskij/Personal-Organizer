package interfaces.impls;

import interfaces.ITask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Task;
import testData.TestData;

public class CollectionTasks implements ITask{

    private static ObservableList<Task> taskList = FXCollections.observableArrayList();

    public static void addTask(Task task) {
        taskList.add(task);
    }

    public void updateTask(Task task) {

    }

    public static void deleteTask(Task task) {
        taskList.remove(task);
    }

    public void fillTaskList(){
        if (taskList.size() == 0) {
            taskList = TestData.createTasks();
        }
        System.out.println(taskList.size());
    }

    public static ObservableList<Task> getTaskList() {
        return taskList;
    }

    public static void setTaskList(ObservableList<Task> taskList) {
        CollectionTasks.taskList = taskList;
    }
}
