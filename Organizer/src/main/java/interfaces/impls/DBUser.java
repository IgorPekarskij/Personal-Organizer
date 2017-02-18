package interfaces.impls;

import interfaces.IUser;
import objects.User;
import utils.Connections;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUser implements IUser {
    private static User currentUser;
    private static boolean existUser = false;

    public DBUser(User newUser) {
        currentUser = newUser;
    }

    public DBUser() {
        String query = "SELECT * FROM Users;";
        Connection connection = Connections.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                currentUser = new User(resultSet.getString("Login"), resultSet.getString("Password"), resultSet.getString("UserName"));
                System.out.println(currentUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
            }
        }
        if (currentUser != null) existUser = true;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User curUser) {
        currentUser = curUser;
    }

    @Override
    public void updateUser() {
        Connection connection = Connections.getConnection();
        Statement statement = null;
        String query;
        try {
            statement = connection.createStatement();
            if (!existUser) {
                query = "INSERT INTO Users (Id, Login, Password, UserName) " + "VALUES (1, '" + currentUser.getLogin() + "', " + "'" + currentUser.getPassword() + "', " +
                        "'" + currentUser.getUserName() + "')";
            } else {
                query = "UPDATE Users SET Login ='" + currentUser.getLogin() + "', Password ='" + currentUser.getPassword() + "', UserName = '" + currentUser.getUserName() + "'" + "WHERE Id = 1";
            }
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
            }
        }
    }
}
