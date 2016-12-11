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
    public Label welcomeLabel;
    @FXML
    private Button createNewTaskButton;
    @FXML
    private Button createNewNoteButton;
    @FXML
    private Button openContactsWindowButton;
    @FXML
    private Button openNotesWindowButton;
    @FXML
    private Button openTasksWindowButton;
    @FXML
    private Button createNewContactButton;

    @FXML
    private void initialize() {
        welcomeLabel.setText("Добро пожаловать, " + TestData.userName);
    }

    public void exitApplication(ActionEvent event) {
        System.exit(0);
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
        newContact.show();
    }

    public void openNotes(ActionEvent event) throws IOException {
        Parent notesScene = FXMLLoader.load(getClass().getResource("/fxmls/notesWindow.fxml"));
        Stage welcomeScene = (Stage) openNotesWindowButton.getScene().getWindow();
        welcomeScene.close();
        Stage notesStage = new Stage();
        notesStage.setTitle("Organizer");
        notesStage.setScene(new Scene(notesScene));
        notesStage.show();
    }

    public void openTasks(ActionEvent event) throws IOException{
        Parent tasksScene = FXMLLoader.load(getClass().getResource("/fxmls/tasksWindow.fxml"));
        Stage welcomeScene = (Stage) openTasksWindowButton.getScene().getWindow();
        welcomeScene.close();
        Stage tasksStage = new Stage();
        tasksStage.setTitle("Organizer");
        tasksStage.setScene(new Scene(tasksScene));
        tasksStage.show();
    }

    public void openContacts(ActionEvent event) throws IOException{
        Parent contactsScene = FXMLLoader.load(getClass().getResource("/fxmls/contactsWindow.fxml"));
        Stage loginStage = (Stage) openContactsWindowButton.getScene().getWindow();
        loginStage.close();
        Stage contactsStage = new Stage();
        contactsStage.setTitle("Organizer");
        contactsStage.setScene(new Scene(contactsScene));
        contactsStage.show();
    }
}
