package controllers;

import interfaces.impls.CollectionContacts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Person;

public class CreateNewContactController {
@FXML
    public Button clearNewUserFieldButton;
    @FXML
    private Button cancelNewUserButton;
    @FXML
    private TextField phoneNewContactField;
    @FXML
    private TextField emailNewContactField;
    @FXML
    private TextField addressNewContactField;
    @FXML
    private TextField surnameNewContactField;
    @FXML
    private TextField nameNewContactField;
    @FXML
    private TextField middlnameNewContactField;
    @FXML
    private DatePicker birthdayDataPicker;
    @FXML
    private TextArea noteTextArea;

    public void createNewUser(ActionEvent event) {
        Person newPerson = new Person(surnameNewContactField.getText(), nameNewContactField.getText(), middlnameNewContactField.getText(), phoneNewContactField.getText(),
                emailNewContactField.getText(), addressNewContactField.getText(), birthdayDataPicker.getValue().toString(), noteTextArea.getText());
        CollectionContacts.addContact(newPerson);
        closeNewUserWindow(event);
    }

    public void closeNewUserWindow(ActionEvent event) {
        Stage newContactStage = (Stage) cancelNewUserButton.getScene().getWindow();
        newContactStage.close();
    }


    public void clearFields(ActionEvent event) {
        surnameNewContactField.setText("");
        nameNewContactField.setText("");
        middlnameNewContactField.setText("");
        phoneNewContactField.setText("");
        emailNewContactField.setText("");
        addressNewContactField.setText("");
        birthdayDataPicker.setValue(null);
        noteTextArea.setText("");
    }


}
