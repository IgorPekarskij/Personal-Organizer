package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import testData.TestData;

public class CreateNewUserController {
    @FXML
    public TextField newUserLoginField;
    @FXML
    public PasswordField newUserPasswordField;
    @FXML
    public TextField newUserNameFiled;
    @FXML
    public Button cancelButton;
    @FXML
    public Button registerButton;

    public void backToLogin(ActionEvent event) {
        Stage newUserStage = (Stage) cancelButton.getScene().getWindow();
        newUserStage.close();
    }

    public void createNewUser(ActionEvent event) {
        TestData.login = newUserLoginField.getText();
        TestData.password = newUserPasswordField.getText();
        TestData.userName = newUserNameFiled.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Organizer");
        alert.getDialogPane().setPrefWidth(500);
        alert.setHeaderText("Спасибо за регистрацию.");
        alert.setContentText("Для входа введите ваш логин и пароль!");
        alert.showAndWait();
        Stage newUserStage = (Stage) registerButton.getScene().getWindow();
        newUserStage.close();
    }
}
