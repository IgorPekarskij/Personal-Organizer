package utils;

import java.sql.*;

public class Connections {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
            String url = "jdbc:sqlite::resource:DB/OrganizerDB";
            if (connection == null) connection = DriverManager.getConnection(url);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
        }
    }
}
