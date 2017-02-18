package controllers;

import interfaces.impls.CollectionContacts;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.Contact;
import utils.ConvertData;

import java.io.IOException;

public class ContactsWindowController {
    private Contact currentPerson;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader;
    private CreateNewContactController createNewContact;
    private Stage editDialogStage;
    private static boolean addPerson = true;
    @FXML
    private TextField contactSearchField;
    @FXML
    private ImageView contactImage;
    @FXML
    public Button updateContactButton;
    @FXML
    private Label fioCurrentContact;
    @FXML
    private Label telephoneCurrentContact;
    @FXML
    private Label emailCurrentContact;
    @FXML
    private Label addressCurrentContact;
    @FXML
    private Label birthdayCurrentContact;
    @FXML
    private TextArea noteCurrentContact;
    @FXML
    private TableView listOfContact;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn phoneColumn;
    @FXML
    private Label countFoundRecords;

    @FXML
    private void initialize() {
        //Fill table.
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        initListeners();
        initLoader();
        fillData();
        FilteredList<Contact> filteredList = new FilteredList<>(CollectionContacts.getPersonsList(), p -> true);
        contactSearchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        }));
        SortedList<Contact> sortedList = new SortedList<Contact>(filteredList);
        sortedList.comparatorProperty().bind(listOfContact.comparatorProperty());
        listOfContact.setItems(sortedList);
        updateCountLabel();
    }

    private void fillData() {
        listOfContact.setItems(CollectionContacts.getPersonsList());
        listOfContact.getSelectionModel().selectFirst();
        showPersonDetail((Contact) listOfContact.getSelectionModel().getSelectedItem());
    }

    private void initListeners() {
        //Displays information to current contact
        listOfContact.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showPersonDetail(newValue))
        );
        //Open edit window by double click on current contact
        listOfContact.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    createNewContact.setPerson((Contact) listOfContact.getSelectionModel().getSelectedItem());
                    editDialogStage = showWindow(editDialogStage, listOfContact, fxmlEdit);
                    showPersonDetail((Contact) listOfContact.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    private void initLoader() {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxmls/newContactWindow.fxml"));
            fxmlEdit = fxmlLoader.load();
            createNewContact = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPersonDetail(Object person) {
        if (person == null) return;
        currentPerson = (Contact) person;
        fioCurrentContact.setText(currentPerson.getSurname() + " " + currentPerson.getName() + " " + currentPerson.getMiddleName());
        telephoneCurrentContact.setText(currentPerson.getPhoneNumber());
        emailCurrentContact.setText(currentPerson.getEmail());
        addressCurrentContact.setText(currentPerson.getAddress());
        birthdayCurrentContact.setText(currentPerson.getBirthday());
        noteCurrentContact.setText(currentPerson.getPersonNote());
        contactImage.setImage(ConvertData.convertToImage(currentPerson.getPersonImage()));
    }

    public void createNewContact(ActionEvent event) throws IOException {
        Object currentEvent = event.getSource();
        if (!(currentEvent instanceof Button)) {
            return;
        }
        Button currentButton = (Button) currentEvent;
        switch (currentButton.getId()) {
            case "newContactButton":
                createNewContact.setPerson(new Contact());
                editDialogStage = showWindow(editDialogStage, listOfContact, fxmlEdit);
                if (addPerson) {
                    int id = CollectionContacts.addContact(createNewContact.getNewPerson());
                    createNewContact.getNewPerson().setPersonID(id);
                    listOfContact.getSelectionModel().selectLast();
                }
                break;
            case "updateContactButton":
                createNewContact.setPerson((Contact) listOfContact.getSelectionModel().getSelectedItem());
                editDialogStage = showWindow(editDialogStage, listOfContact, fxmlEdit);
                showPersonDetail((Contact) listOfContact.getSelectionModel().getSelectedItem());
                listOfContact.refresh();
                break;
        }
    }

    public void deleteContact(ActionEvent event) {
        int rowNumber = listOfContact.getSelectionModel().getSelectedIndex();
        if (rowNumber < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Органайзер");
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText("Выберите задачу!");
            alert.showAndWait();
        } else {
            ButtonType ok = new ButtonType("Да", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("Нет", ButtonBar.ButtonData.NO);
            Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION, "Вы действительно хотите удалить контакт?", ok, no);
            confirmExit.setHeaderText("");
            confirmExit.setTitle("Удаление контакта!");
            confirmExit.showAndWait();
            if (confirmExit.getResult() == ok) {
                CollectionContacts.deleteContact((Contact) listOfContact.getSelectionModel().getSelectedItem());
                fioCurrentContact.setText(" ");
                telephoneCurrentContact.setText("");
                emailCurrentContact.setText("");
                birthdayCurrentContact.setText("");
                addressCurrentContact.setText("");
                noteCurrentContact.setText("");
                contactImage.setImage(null);
            }
            listOfContact.refresh();
            listOfContact.getSelectionModel().selectFirst();
        }
    }

    private void updateCountLabel() {
        countFoundRecords.textProperty().bind(Bindings.size(listOfContact.getItems()).asString("Найдено %s записей"));
    }

    public Stage showWindow(Stage stage, TableView tableView, Parent fxmlEdit) {
        if (stage == null) {
            stage = new Stage();
            stage.setTitle("Органайзер");
            stage.setResizable(true);
            stage.setScene(new Scene(fxmlEdit));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableView.getParent().getScene().getWindow());
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();

                }
            });
        }
        stage.showAndWait();
        return stage;
    }

    public static void setAddPerson(boolean addPerson) {
        ContactsWindowController.addPerson = addPerson;
    }

    public void exitApplication(ActionEvent event) {
        LoginWindowController.exitApplication();
    }

    public void openTasks(ActionEvent event) throws IOException {
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/tasksWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfContact, contactScene);
    }

    public void openNotes(ActionEvent event) throws IOException {
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/notesWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfContact, contactScene);
    }

    public void toWelcome(ActionEvent actionEvent) throws IOException {
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/welcomeWindow.fxml"));
        LoginWindowController.openWelcomeWindow(welcomeScene, listOfContact);
    }


    public void changeUser(ActionEvent actionEvent) throws IOException {
        Parent registerUser = FXMLLoader.load(getClass().getResource("/fxmls/registartionForm.fxml"));
        LoginWindowController.changeUserWindow(registerUser, listOfContact);
    }
}