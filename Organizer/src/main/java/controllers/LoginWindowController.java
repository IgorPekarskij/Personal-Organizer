package controllers;

import interfaces.impls.CollectionContacts;
import interfaces.impls.CollectionNotes;
import interfaces.impls.CollectionTasks;
import interfaces.impls.DBUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LoginWindowController {
    private static String title = "Органайзер";
    private static String confirmationTitle = "Подтверждение!";
    private static String confirmationMessage = "Вы хотите закрыть приложение?";
    private static String wrongCredentialHeader = "Неверный логин или пароль";
    private static DBUser user;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button exitButton;
    @FXML
    private HBox loginWindowButtonsPane;
    @FXML
    private Button registerButton;
    @FXML
    private Button enterButton;

    @FXML
    private void initialize() {
        user = new DBUser();
        if (user.getCurrentUser() != null) {
            hideRegistrationButton();
        } else {
            enterButton.setDisable(true);
        }

    }



    private void hideRegistrationButton() {
        registerButton.setVisible(false);
        loginWindowButtonsPane.setPadding(new Insets(10, 10, 10, 110));
        enterButton.setMinWidth(115);
        exitButton.setMinWidth(115);
    }

    public void exitApplication(ActionEvent event) {
        exitApplication();
    }

    public void openRegistrationForm(ActionEvent event) throws IOException {
        Parent registerUser = FXMLLoader.load(getClass().getResource("/fxmls/registartionForm.fxml"));
        changeUserWindow(registerUser, registerButton);
        if (user.getCurrentUser() != null) {
            enterButton.setDisable(false);
            hideRegistrationButton();
        }
    }

    public void checkCredentials(ActionEvent event) throws IOException {
        if (loginField.getText().equals(DBUser.getCurrentUser().getLogin()) && passwordField.getText().equals(DBUser.getCurrentUser().getPassword())) {
            CollectionContacts collectionContacts = new CollectionContacts();
            collectionContacts.fillContactList();
            CollectionNotes collectionNotes = new CollectionNotes();
            collectionNotes.fillNoteList();
            CollectionTasks collectionTasks = new CollectionTasks();
            collectionTasks.fillTaskList();
            Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/welcomeWindow.fxml"));
            openWelcomeWindow(welcomeScene, enterButton);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(wrongCredentialHeader);
            alert.getDialogPane().setPrefWidth(500);
            alert.showAndWait();
        }
    }

    public static void exitApplication() {
        ButtonType ok = new ButtonType(ContactsWindowController.getConfirmButtonLabel(), ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(ContactsWindowController.getCancelButtonLabel(), ButtonBar.ButtonData.NO);
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage, ok, no);
        confirmExit.setHeaderText(ContactsWindowController.getEmptyString());
        confirmExit.setTitle(confirmationTitle);
        confirmExit.showAndWait();
        if (confirmExit.getResult() == ok) {
            System.exit(0);
        }
    }

    public static void openWelcomeWindow(Parent parent, Node node) {
        Stage loginStage = (Stage) node.getScene().getWindow();
        Stage root = new Stage();
        root.setTitle(title);
        root.setResizable(false);
        root.setScene(new Scene(parent, 650, 465));
        root.show();
        loginStage.close();
        root.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                LoginWindowController.exitApplication();
            }
        });
    }

    public static void changeUserWindow(Parent parent, Node node) {
        Stage newUser = new Stage();
        newUser.setTitle(title);
        newUser.setResizable(false);
        newUser.initModality(Modality.WINDOW_MODAL);
        newUser.initOwner(node.getScene().getWindow());
        newUser.setScene(new Scene(parent));
        newUser.showAndWait();
    }

    public static DBUser getUser() {
        return user;
    }

    public static String getTitle() {
        return title;
    }

    public static void setUser(DBUser user) {
        LoginWindowController.user = user;
    }
}
