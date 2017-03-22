package controllers;

import interfaces.impls.CollectionContacts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Contact;
import utils.ConvertData;

import java.io.File;

public class CreateNewContactController {
    @FXML
    private TextField country;
    @FXML
    private TextField city;
    @FXML
    private TextField address;
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
    private TextField surnameNewContactField;
    @FXML
    private TextField nameNewContactField;
    @FXML
    private TextField middlnameNewContactField;
    @FXML
    private DatePicker birthdayDataPicker;
    @FXML
    private TextArea noteTextArea;
    private Contact newPerson;

    private byte[] tempImage = null;

    @FXML
    private void initialize() {
        chooseImage(photoOpenButton, personImage);
    }

    public Contact getNewPerson() {
        return newPerson;
    }

    public void setPerson(Contact person) {
        if (person != null) {
            this.newPerson = person;
            surnameNewContactField.setText(newPerson.getSurname());
            nameNewContactField.setText(newPerson.getName());
            middlnameNewContactField.setText(newPerson.getMiddleName());
            phoneNewContactField.setText(newPerson.getPhoneNumber());
            emailNewContactField.setText(newPerson.getEmail());
            country.setText(newPerson.getCountry());
            city.setText(newPerson.getCountry());
            address.setText(newPerson.getAddress());
            noteTextArea.setText(newPerson.getPersonNote());
            birthdayDataPicker.setValue(ConvertData.convertStringToLocalDate(newPerson.getBirthday()));
            if (newPerson.getPersonImage() != null) {
                personImage.setImage(ConvertData.convertToImage(newPerson.getPersonImage()));
            }
        }
    }

    public void saveContact(ActionEvent event) {
        if (!surnameNewContactField.getText().isEmpty()) {
            this.newPerson.setSurname(surnameNewContactField.getText());
            this.newPerson.setName(nameNewContactField.getText().isEmpty() ? " " : nameNewContactField.getText());
            this.newPerson.setMiddleName(middlnameNewContactField.getText().isEmpty() ? " " : middlnameNewContactField.getText());
            this.newPerson.setPhoneNumber(phoneNewContactField.getText().isEmpty() ? " " : phoneNewContactField.getText());
            this.newPerson.setEmail(emailNewContactField.getText().isEmpty() ? " " : emailNewContactField.getText());
            this.newPerson.setCountry(country.getText().isEmpty() ? " " : country.getText());
            this.newPerson.setCity(city.getText().isEmpty() ? " " : city.getText());
            this.newPerson.setAddress(address.getText().isEmpty() ? " " : address.getText());
            this.newPerson.setBirthday(birthdayDataPicker.getValue() == null ? " " : ConvertData.convertLocalDateToString(birthdayDataPicker.getValue()));
            this.newPerson.setPersonNote(noteTextArea.getText().isEmpty() ? " " : noteTextArea.getText());
            this.newPerson.setPersonImage(personImage.getImage() == null ? new byte[0] : tempImage);
            ContactsWindowController.setAddPerson(true);
            hideWindow(cancelSaveUserButton);
            CollectionContacts.updateContact(newPerson);
            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Органайзер");
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText("Заполните фамилию контакта!");
            alert.setContentText("Поле фамилия не может быть пустым!");
            alert.showAndWait();
        }
    }

    public void cancelSaveContact(ActionEvent event) {
        ContactsWindowController.setAddPerson(false);
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
        country.setText("");
        city.setText("");
        address.setText("");
        birthdayDataPicker.setValue(null);
        noteTextArea.setText("");
        personImage.setImage(null);
    }

    public void setTempImage(byte[] tempImage) {
        this.tempImage = tempImage;
    }

    public byte[] getTempImage() {
        return tempImage;
    }

    public void addPhotoButton(ActionEvent event) {
        chooseImage(photoOpenButton, personImage);
    }

    public void chooseImage(Button button, ImageView imageView) {
        final FileChooser choose = new FileChooser();
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                choose.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG", "*.png"),
                        new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                        new FileChooser.ExtensionFilter("GIF", "*.gif")
                );
                if (button != null) {
                    File file = choose.showOpenDialog(button.getScene().getWindow());
                    if (file != null) {
                        byte[] bImage = ConvertData.convertFileToByteArray(file);
                        setTempImage(bImage);
                        imageView.setImage(ConvertData.convertToImage(bImage));
                    }
                }
            }
        });
    }

    public void exitApplication(ActionEvent actionEvent) {
        LoginWindowController.exitApplication();
    }
}
