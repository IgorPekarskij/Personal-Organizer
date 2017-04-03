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
    private String successRegistrationHeader = "Спасибо за регистрацию.";
    private String successRegistrationMessage = "Для входа введите ваш логин и пароль!";
    private String errorRegistrationHeader = "Заполните поле \"Ваше имя\"!";
    private String errorRegistrationMessage = "Поле \"Ваше имя\" не может быть пустым!";

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
            Alert successRegistration = new Alert(Alert.AlertType.INFORMATION);
            successRegistration.setTitle(LoginWindowController.getTitle());
            successRegistration.getDialogPane().setPrefWidth(500);
            successRegistration.setHeaderText(successRegistrationHeader);
            successRegistration.setContentText(successRegistrationMessage);
            successRegistration.showAndWait();
            Stage newUserStage = (Stage) registerButton.getScene().getWindow();
            newUserStage.close();
        } else {
            Alert errorRegistration = new Alert(Alert.AlertType.ERROR);
            errorRegistration.setTitle(LoginWindowController.getTitle());
            errorRegistration.getDialogPane().setPrefWidth(500);
            errorRegistration.setHeaderText(errorRegistrationHeader);
            errorRegistration.setContentText(errorRegistrationMessage);
            errorRegistration.showAndWait();
        }
    }
}
