package controllers;

import interfaces.impls.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.User;

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
        if (!newUserNameFiled.getText().isEmpty()) {
            User newUser = new User(newUserLoginField.getText(), newUserPasswordField.getText(), newUserNameFiled.getText());
            DBUser dbUser = new DBUser(newUser);
            dbUser.updateUser();
            DBUser.setCurrentUser(newUser);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Органайзер");
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText("Спасибо за регистрацию.");
            alert.setContentText("Для входа введите ваш логин и пароль!");
            alert.showAndWait();
            Stage newUserStage = (Stage) registerButton.getScene().getWindow();
            newUserStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Органайзер");
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText("Заполните поле \"Ваше имя\"!");
            alert.setContentText("Поле \"Ваше имя\" не может быть пустым!");
            alert.showAndWait();
        }
    }
}
