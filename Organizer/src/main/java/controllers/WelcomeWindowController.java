package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import testData.TestData;

import java.io.IOException;

public class WelcomeWindowController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button openContactsWindowButton;
    @FXML
    private Button openNotesWindowButton;
    @FXML
    private Button openTasksWindowButton;
    @FXML
    private void initialize() {
        welcomeLabel.setText("Добро пожаловать, " + TestData.getUser().getUserName());
    }

    public void exitApplication(ActionEvent event) {
        LoginWindowController.exitApplication();
    }

    public void openNewTask(ActionEvent event) throws IOException{
        Parent newTaskScene = FXMLLoader.load(getClass().getResource("/fxmls/newTaskWindow.fxml"));
        Stage newTask = new Stage();
        newTask.setTitle("Organizer");
        newTask.setResizable(false);
        newTask.setScene(new Scene(newTaskScene));
        newTask.initModality(Modality.WINDOW_MODAL);
        newTask.initOwner(((Node) event.getSource()).getScene().getWindow());
        newTask.show();
    }

    public void openNewNote(ActionEvent event) throws IOException{
        Parent newNoteScene = FXMLLoader.load(getClass().getResource("/fxmls/newNoteWindow.fxml"));
        Stage newNote = new Stage();
        newNote.setTitle("Organizer");
        newNote.setResizable(false);
        newNote.setScene(new Scene(newNoteScene));
        newNote.initModality(Modality.WINDOW_MODAL);
        newNote.initOwner(((Node) event.getSource()).getScene().getWindow());
        newNote.show();
    }

    public void openNewContact(ActionEvent event) throws IOException{
        Parent newContactScene = FXMLLoader.load(getClass().getResource("/fxmls/newContactWindow.fxml"));
        Stage newContact = new Stage();
        newContact.setTitle("Organizer");
        newContact.setResizable(false);
        newContact.setScene(new Scene(newContactScene));
        newContact.initModality(Modality.WINDOW_MODAL);
        newContact.initOwner(((Node) event.getSource()).getScene().getWindow());
        newContact.showAndWait();
    }

    public void openNotes(ActionEvent event) throws IOException {
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/notesWindow.fxml"));
        openNewWindow(openNotesWindowButton, welcomeScene);
    }

    public void openTasks(ActionEvent event) throws IOException{
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/tasksWindow.fxml"));
        openNewWindow(openTasksWindowButton, welcomeScene);
    }

    public void openContacts(ActionEvent event) throws IOException{
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/contactsWindow.fxml"));
        openNewWindow(openContactsWindowButton, welcomeScene);
    }

    public static void openNewWindow(Node button, Parent scene) throws IOException {
        Stage welcomeStage = (Stage) button.getScene().getWindow();
        Stage contactsStage = new Stage();
        contactsStage.setTitle("Organizer");
        contactsStage.setScene(new Scene(scene));
        contactsStage.show();
        welcomeStage.close();
    }
}
