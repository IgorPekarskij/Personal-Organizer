package interfaces.impls;

import interfaces.INote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Note;
import utils.Connections;
import java.sql.*;

public class CollectionNotes implements INote {

    static ObservableList<Note> notesList = FXCollections.observableArrayList();

    public static int addNote(Note note) {
        int noteId = -1;
        notesList.add(note);
        Connection connection = Connections.getConnection();
        String query = "INSERT INTO Notes (Name, Description, Date,  Image) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        try {
            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, note.getName());
            ps.setString(2, note.getDescription());
            ps.setString(3, note.getNoteDate());
            ps.setBytes(4, note.getNoteImage());
            ps.executeUpdate();
            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                noteId = generatedKeys.getInt(1);
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
        return noteId;
    }

    public static void updateNote(Note note) {
        Connection connection = Connections.getConnection();
        String query = "UPDATE Notes SET Name = ?, Description = ?, Date = ?, Image = ? WHERE Id = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, note.getName());
            ps.setString(2, note.getDescription());
            ps.setString(3, note.getNoteDate());
            ps.setBytes(4, note.getNoteImage());
            ps.setInt(5, note.getNoteID());
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

    public static ObservableList<Note> getNotesList() {
        return notesList;
    }

    public static void deleteNote(Note note) {
        notesList.remove(note);
        Connection connection = Connections.getConnection();
        Statement statement = null;
        String query;
        try {
            statement = connection.createStatement();
            query = "DELETE FROM Notes WHERE ID=" + note.getNoteID();
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

    public void fillNoteList() {
        Connection connection = Connections.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Notes;";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                notesList.add(new Note(resultSet.getInt("Id"), resultSet.getString("Name"), resultSet.getString("Description"),
                        resultSet.getString("Date"),
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
}
