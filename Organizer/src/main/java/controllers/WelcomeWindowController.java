package controllers;

import interfaces.impls.CollectionTasks;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.ConvertData;

import java.io.IOException;

public class WelcomeWindowController {
    @FXML
    private Label countTasks;
    @FXML
    private Label taskToday;
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
        welcomeLabel.setText("Добро пожаловать, " + LoginWindowController.getUser().getCurrentUser().getUserName());
        countTasks.setText("Выполнено задач: " + ConvertData.countCompletedTasks() + " из " + CollectionTasks.getTaskList().size());
        taskToday.setText("Задач на сегодня: " + ConvertData.countTodayTasks());
    }

    public void exitApplication(ActionEvent event) {
        LoginWindowController.exitApplication();
    }

    public void openNotes(ActionEvent event) throws IOException {
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/notesWindow.fxml"));
        openNewWindow(openNotesWindowButton, welcomeScene);
    }

    public void openTasks(ActionEvent event) throws IOException {
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/tasksWindow.fxml"));
        openNewWindow(openTasksWindowButton, welcomeScene);
    }

    public void openContacts(ActionEvent event) throws IOException {
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/contactsWindow.fxml"));
        openNewWindow(openContactsWindowButton, welcomeScene);
    }

    public static void openNewWindow(Node button, Parent scene) throws IOException {
        Stage welcomeStage = (Stage) button.getScene().getWindow();
        Stage contactsStage = new Stage();
        contactsStage.setTitle("Органайзер");
        contactsStage.setScene(new Scene(scene));
        contactsStage.show();
        welcomeStage.close();
        contactsStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                LoginWindowController.exitApplication();
            }
        });
    }
}
