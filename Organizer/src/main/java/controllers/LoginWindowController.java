package controllers;

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
            Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/welcomeWindow.fxml"));
            openWelcomeWindow(welcomeScene, enterButton);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Органайзер");
            alert.setHeaderText("Неверный логин или пароль");
            alert.getDialogPane().setPrefWidth(500);
            alert.setContentText("Введите верные учетные данные!");
            alert.showAndWait();
        }
    }

    public static void exitApplication() {
        ButtonType ok = new ButtonType("Да", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("Нет", ButtonBar.ButtonData.NO);
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION, "Вы хотите закрыть приложение?", ok, no);
        confirmExit.setHeaderText("");
        confirmExit.setTitle("Подтверждение!");
        confirmExit.showAndWait();
        if (confirmExit.getResult() == ok) {
            System.exit(0);
        }
    }

    public static void openWelcomeWindow(Parent parent, Node node) {
        Stage loginStage = (Stage) node.getScene().getWindow();
        Stage root = new Stage();
        root.setTitle("Органайзер");
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
        newUser.setTitle("Органайзер");
        newUser.setResizable(false);
        newUser.initModality(Modality.WINDOW_MODAL);
        newUser.initOwner(node.getScene().getWindow());
        newUser.setScene(new Scene(parent));
        newUser.showAndWait();
    }

    public static DBUser getUser() {
        return user;
    }

    public static void setUser(DBUser user) {
        LoginWindowController.user = user;
    }
}
