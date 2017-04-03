package interfaces.impls;

import interfaces.IContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import objects.Contact;
import utils.Connections;
import java.sql.*;
import java.util.List;

public class CollectionContacts implements IContact {
    private static ObservableList<Contact> personsList = FXCollections.observableArrayList();

    public static int addContact(Contact person) {
        int personId = -1;
        Connection connection = Connections.getConnection();
        personsList.add(person);
        String query = "INSERT INTO Contacts (Surname, Name, MiddleName, Phone, Email, Country, City, Address, Birthday, Note, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        try {
            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, person.getSurname());
            ps.setString(2, person.getName());
            ps.setString(3, person.getMiddleName());
            ps.setString(4, person.getPhoneNumber());
            ps.setString(5, person.getEmail());
            ps.setString(6, person.getCountry());
            ps.setString(7, person.getCity());
            ps.setString(8, person.getAddress());
            ps.setString(9, person.getBirthday());
            ps.setString(10, person.getPersonNote());
            ps.setBytes(11, person.getPersonImage());
            ps.executeUpdate();
            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                personId = generatedKeys.getInt(1);
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
        return personId;
    }

    public static void updateContact(Contact person) {
        Connection connection = Connections.getConnection();
        String query = "UPDATE Contacts SET Surname = ?, Name = ?, MiddleName = ?, Phone = ?, Email = ?, Country = ?, City = ?, Address = ?, Birthday = ?, Note = ?, Image = ? WHERE Id = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, person.getSurname());
            ps.setString(2, person.getName());
            ps.setString(3, person.getMiddleName());
            ps.setString(4, person.getPhoneNumber());
            ps.setString(5, person.getEmail());
            ps.setString(6, person.getCountry());
            ps.setString(7, person.getCity());
            ps.setString(8, person.getAddress());
            ps.setString(9, person.getBirthday());
            ps.setString(10, person.getPersonNote());
            ps.setBytes(11, person.getPersonImage());
            ps.setInt(12, person.getPersonID());
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

    public static void deleteContact(Contact person) {
        Connection connection = Connections.getConnection();
        Statement statement = null;
        personsList.remove(person);
        String query;
        try {
            statement = connection.createStatement();
            query = "DELETE FROM Contacts WHERE ID=" + person.getPersonID();
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

    public static void fillContactList() {
        Connection connection = Connections.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Contacts;";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                personsList.add(new Contact(resultSet.getInt("Id"), resultSet.getString("Surname"), resultSet.getString("Name"),
                        resultSet.getString("MiddleName"), resultSet.getString("Phone"), resultSet.getString("Email"),
                        resultSet.getString("Country"), resultSet.getString("City"), resultSet.getString("Address"),
                        resultSet.getString("Birthday"), resultSet.getString("Note"),
                        resultSet.getBytes("Image") == null ? null : resultSet.getBytes("Image")));
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

    public static ObservableList<Contact> getPersonsList() {
        return personsList;
    }

    public static void addContact(List<List<Contact>> persons) {
        Connection connection = Connections.getConnection();
        int countContacts = 0;
        String query = "INSERT INTO Contacts (Surname, Name, MiddleName, Phone, Email, Country, City, Address, Birthday, Note, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            for (List<Contact> item: persons ) {
                for (Contact contact : item) {
                    countContacts++;
                    ps.setString(1, contact.getSurname());
                    ps.setString(2, contact.getName());
                    ps.setString(3, contact.getMiddleName());
                    ps.setString(4, contact.getPhoneNumber());
                    ps.setString(5, contact.getEmail());
                    ps.setString(6, contact.getCountry());
                    ps.setString(7, contact.getCity());
                    ps.setString(8, contact.getAddress());
                    ps.setString(9, contact.getBirthday());
                    ps.setString(10, contact.getPersonNote());
                    ps.setBytes(11, contact.getPersonImage());
                    ps.addBatch();
                }
                    ps.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Контакты импортированы");
        alert.getDialogPane().setPrefWidth(500);
        alert.setHeaderText("Загружено " + countContacts + " контактов!");
        alert.showAndWait();
        personsList.clear();
        CollectionContacts.fillContactList();

    }

}
