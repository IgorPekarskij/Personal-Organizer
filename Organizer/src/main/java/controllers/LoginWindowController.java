package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import testData.TestData;

import java.io.IOException;

public class LoginWindowController {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    private Button enterButton;


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
        newUser.show();


    }

    public void checkCredentials(ActionEvent event) throws IOException{

        if(loginField.getText().equals(TestData.login) && passwordField.getText().equals(TestData.password)) {
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
