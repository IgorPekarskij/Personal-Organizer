package interfaces.impls;

import interfaces.ITask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import objects.Task;
import utils.Connections;
import java.sql.*;
import java.util.List;

public class CollectionTasks implements ITask {

    private static ObservableList<Task> taskList = FXCollections.observableArrayList();

    public static int addTask(Task task) {
        int taskId = -1;
        Connection connection = Connections.getConnection();
        taskList.add(task);
        String query = "INSERT INTO Tasks (Name, Description, StartDate, EndDate, StartTime, EndTime, Image, Completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStartDate());
            ps.setString(4, task.getEndDate());
            ps.setString(5, task.getStartTime());
            ps.setString(6, task.getEndTime());
            ps.setBytes(7, task.getTaskImage());
            ps.setBoolean(8, task.isCompleted());
            ps.executeUpdate();
            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                taskId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (generatedKeys != null) generatedKeys.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return taskId;
    }

    public static void updateTask(Task task) {
        Connection connection = Connections.getConnection();
        String query = "UPDATE Tasks SET Name = ?, Description = ?, StartDate = ?, EndDate = ?, StartTime = ?, EndTime = ?, Image = ?, Completed = ? WHERE Id = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStartDate());
            ps.setString(4, task.getEndDate());
            ps.setString(5, task.getStartTime());
            ps.setString(6, task.getEndTime());
            ps.setBytes(7, task.getTaskImage());
            ps.setBoolean(8, task.isCompleted());
            ps.setInt(9, task.getTaskID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteTask(Task task) {
        taskList.remove(task);
        Connection connection = Connections.getConnection();
        Statement statement = null;
        String query;
        try {
            statement = connection.createStatement();
            query = "DELETE FROM Tasks WHERE ID=" + task.getTaskID();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fillTaskList() {
        Connection connection = Connections.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Tasks;";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                taskList.add(new Task(resultSet.getInt("Id"), resultSet.getString("Name"),
                        resultSet.getString("Description"), resultSet.getString("StartDate"), resultSet.getString("EndDate"),
                        resultSet.getString("StartTime"), resultSet.getString("EndTime"),
                        resultSet.getBytes("Image") == null ? null : resultSet.getBytes("Image"),
                        resultSet.getBoolean("Completed")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ObservableList<Task> getTaskList() {
        return taskList;
    }

    public static void addTask(List<List<Task>> tasks) {
    Connection connection = Connections.getConnection();
    int countTasks = 0;
    String query = "INSERT INTO Tasks (Name, Description, StartDate, EndDate, StartTime, EndTime, Image, Completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
        PreparedStatement ps = connection.prepareStatement(query);
        for (List<Task> item: tasks ) {
            for (Task task : item) {
                countTasks++;
                ps.setString(1, task.getName());
                ps.setString(2, task.getDescription());
                ps.setString(3, task.getStartDate());
                ps.setString(4, task.getEndDate());
                ps.setString(5, task.getStartTime());
                ps.setString(6, task.getEndTime());
                ps.setBytes(7, task.getTaskImage());
                ps.setBoolean(8, task.isCompleted());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Задачи импортированы");
        alert.getDialogPane().setPrefWidth(500);
        alert.setHeaderText("Загружено " + countTasks + " задач!");
        alert.showAndWait();
        taskList.clear();
        fillTaskList();

}
}
