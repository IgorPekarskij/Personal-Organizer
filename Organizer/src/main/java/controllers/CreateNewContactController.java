package controllers;

import interfaces.impls.CollectionContacts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Person;
import utils.ConvertData;
import java.io.File;

public class CreateNewContactController {
    @FXML
    private ImageView personImage;
    @FXML
    private Button photoOpenButton;
    @FXML
    private Button cancelSaveUserButton;
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
    private Person newPerson;

    public Person getNewPerson() {
        return newPerson;
    }

    public void setPerson(Person person) {
        if (person != null) {
            this.newPerson = person;
            surnameNewContactField.setText(newPerson.getSurname());
            nameNewContactField.setText(newPerson.getName());
            middlnameNewContactField.setText(newPerson.getMiddleName());
            phoneNewContactField.setText(newPerson.getPhoneNumber());
            emailNewContactField.setText(newPerson.getEmail());
            addressNewContactField.setText(newPerson.getAddress());
            noteTextArea.setText(newPerson.getPersonNote());
            birthdayDataPicker.setValue(ConvertData.convertStringToLocalDate(newPerson.getBirthday()));
            if(newPerson.getPersonImage() != null) {
                personImage.setImage(ConvertData.convertToImage(newPerson.getPersonImage()));
            }
        }
    }

    public void saveContact(ActionEvent event) {
        //Исправить после подключения БД
        this.newPerson.setPersonID(CollectionContacts.getPersonsList().get(CollectionContacts.getPersonsList().size()-1).getPersonID()+1);
        //--------
        this.newPerson.setSurname(surnameNewContactField.getText() == null ? " " : surnameNewContactField.getText());
        this.newPerson.setName(nameNewContactField.getText() == null ? " " : nameNewContactField.getText());
        this.newPerson.setMiddleName(middlnameNewContactField.getText() == null ? " " : middlnameNewContactField.getText());
        this.newPerson.setPhoneNumber(phoneNewContactField.getText() == null ? " " : phoneNewContactField.getText());
        this.newPerson.setEmail(emailNewContactField.getText() == null ? " " : emailNewContactField.getText());
        this.newPerson.setAddress(addressNewContactField.getText() == null ? " " : addressNewContactField.getText());
        this.newPerson.setBirthday(birthdayDataPicker.getValue() == null ? " " : ConvertData.convertLocalDateToString(birthdayDataPicker.getValue()));
        this.newPerson.setPersonNote(noteTextArea.getText() == null ? " " : noteTextArea.getText());
        ContactsController.setAddPerson(true);
        hideWindow(cancelSaveUserButton);
    }

    public void cancelSaveContact(ActionEvent event) {
        ContactsController.setAddPerson(false);
        hideWindow(cancelSaveUserButton);
        clearFields();
    }

    public void hideWindow(Node node) {
        Stage newStage = (Stage) node.getScene().getWindow();
        newStage.hide();
    }

    public void clearFields() {
        surnameNewContactField.setText("");
        nameNewContactField.setText("");
        middlnameNewContactField.setText("");
        phoneNewContactField.setText("");
        emailNewContactField.setText("");
        addressNewContactField.setText("");
        birthdayDataPicker.setValue(null);
        noteTextArea.setText("");
        personImage.setImage(null);
    }

    public void addPhotoButton(ActionEvent event) {
        final FileChooser choose = new FileChooser();
        photoOpenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                choose.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
                );
                File file = choose.showOpenDialog(photoOpenButton.getScene().getWindow());
                if (file != null) {
                    byte[] bImage = ConvertData.convertToByteArray(file);
                    newPerson.setPersonImage(bImage);
                    personImage.setImage(ConvertData.convertToImage(bImage));
                }
            }
        });
    }
}
