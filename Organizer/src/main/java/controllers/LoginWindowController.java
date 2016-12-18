package controllers;

import javafx.event.ActionEvent;
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
import testData.TestData;
import testData.User;

import java.io.IOException;

public class LoginWindowController {
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
    private void initialize () {
        User user = TestData.getUser();
        if (user != null) {
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
        System.exit(0);
    }

    public void openRegistrationForm(ActionEvent event) throws IOException {
        Parent registerUser = FXMLLoader.load(getClass().getResource("/fxmls/registartionForm.fxml"));
        Stage newUser = new Stage();
        newUser.setTitle("Organizer");
        newUser.setResizable(false);
        newUser.initModality(Modality.WINDOW_MODAL);
        newUser.initOwner(((Node) event.getSource()).getScene().getWindow());
        newUser.setScene(new Scene(registerUser));
        newUser.showAndWait();
        User user = TestData.getUser();
        if (user != null) {
            enterButton.setDisable(false);
            hideRegistrationButton();
        }
    }

    public void checkCredentials(ActionEvent event) throws IOException{
        if(loginField.getText().equals(TestData.getUser().getLogin()) && passwordField.getText().equals(TestData.getUser().getPassword())) {
            Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/welcomeWindow.fxml"));
            Stage loginStage = (Stage) enterButton.getScene().getWindow();
            loginStage.close();
            Stage root = new Stage();
            root.setTitle("Organizer");
            root.setResizable(false);
            root.setScene(new Scene(welcomeScene, 650, 465));
            root.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Organizer");
            alert.setHeaderText("Неверный логин или пароль");
            alert.getDialogPane().setPrefWidth(500);
            alert.setContentText("Введите верные данные или зарегистрируйтесь!");
            alert.showAndWait();
        }
    }

}
