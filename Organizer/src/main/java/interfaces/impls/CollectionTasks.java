package interfaces.impls;

import interfaces.ITask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Task;
import utils.Connections;

import java.sql.*;

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

    public void fillTaskList() {
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
}
