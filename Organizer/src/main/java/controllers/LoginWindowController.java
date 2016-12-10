package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowController {
    @FXML
    private Button enterButton;

    public void exitApplication(ActionEvent event) {
        System.exit(0);
    }

    public void createNewUser(ActionEvent event) throws IOException {

    }

    public void checkCredentials(ActionEvent event) throws IOException{
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/welcomeWindow.fxml"));
        Stage loginStage = (Stage) enterButton.getScene().getWindow();
        loginStage.close();
        Stage root = new Stage();
        root.setTitle("Organizer");
        root.setResizable(false);
        root.setScene(new Scene(welcomeScene, 650, 465));
        root.show();
    }
}
