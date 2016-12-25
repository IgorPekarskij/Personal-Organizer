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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Person;
import utils.ConvertData;
import java.io.IOException;

public class ContactsController {
    private CollectionContacts collectionContacts = new CollectionContacts();
    private static Person currentPerson;
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
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("PhoneNumber"));
        initListeners();
        initLoader();
        fillData();

        FilteredList <Person> filteredList = new FilteredList<>(collectionContacts.getPersonsList(), p -> true);
        contactSearchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches phone.
                }
                return false; // Does not match.
            });
        }) );

        SortedList<Person> sortedList = new SortedList<Person>(filteredList);
        sortedList.comparatorProperty().bind(listOfContact.comparatorProperty());
        listOfContact.setItems(sortedList);
        updateCountLabel();
    }

    private void fillData() {
        collectionContacts.fillContactList();
        listOfContact.setItems(collectionContacts.getPersonsList());
        listOfContact.getSelectionModel().selectFirst();
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
                if(event.getClickCount() == 2) {
                    createNewContact.setPerson((Person) listOfContact.getSelectionModel().getSelectedItem());
                    editDialogStage = showWindow(editDialogStage, listOfContact, fxmlEdit);
                    showPersonDetail((Person) listOfContact.getSelectionModel().getSelectedItem());
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
        currentPerson = (Person) person;
        fioCurrentContact.setText(currentPerson.getSurname() + " " + currentPerson.getName() + " " + currentPerson.getMiddleName());
        telephoneCurrentContact.setText(currentPerson.getPhoneNumber());
        emailCurrentContact.setText(currentPerson.getEmail());
        addressCurrentContact.setText(currentPerson.getAddress());
        birthdayCurrentContact.setText(currentPerson.getBirthday());
        noteCurrentContact.setText(currentPerson.getPersonNote());
        Image image = ConvertData.convertToImage(currentPerson.getPersonImage());
        if(image != null) {
            contactImage.setImage(image);
            AnchorPane pane = (AnchorPane) contactImage.getParent();
            pane.setCenterShape(true);
        }
    }

    public void createNewContact(ActionEvent event) throws IOException {
        Object currentEvent = event.getSource();
        if(!(currentEvent instanceof Button)){
            return;
        }
        Button currentButton = (Button) currentEvent;
        switch (currentButton.getId()) {
            case "newContactButton" :
                System.out.println(addPerson);
                createNewContact.setPerson(new Person());
                editDialogStage = showWindow(editDialogStage, listOfContact, fxmlEdit);
                System.out.println(addPerson);
                if (addPerson) {
                    collectionContacts.addContact(createNewContact.getNewPerson());
                    listOfContact.getSelectionModel().selectLast();
                }
                break;
            case "updateContactButton" :
                createNewContact.setPerson((Person) listOfContact.getSelectionModel().getSelectedItem());
                editDialogStage = showWindow(editDialogStage, listOfContact, fxmlEdit);
                showPersonDetail((Person)listOfContact.getSelectionModel().getSelectedItem());
                break;
        }
    }

    public void deleteContact(ActionEvent event) {
        CollectionContacts.deleteContact((Person) listOfContact.getSelectionModel().getSelectedItem());
    }

    private void updateCountLabel(){
        countFoundRecords.textProperty().bind(Bindings.size(listOfContact.getItems()).asString("Найдено %s записей"));
    }

    public static Person getCurrentPerson() {
        return currentPerson;
    }

    public Stage showWindow(Stage stage, TableView tableView, Parent fxmlEdit) {
        if(stage == null) {
            System.out.println("New Stage");
            stage = new Stage();
            stage.setTitle("Organizer");
            stage.setResizable(false);
            stage.setScene(new Scene(fxmlEdit));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableView.getParent().getScene().getWindow());
        }
        stage.showAndWait();
        return stage;
    }

    public static void setAddPerson(boolean addPerson) {
        ContactsController.addPerson = addPerson;
    }

    public void exitApplication(ActionEvent event) {
       LoginWindowController.exitApplication();
    }


    public void openContacts(ActionEvent event) throws IOException{
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/contactsWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfContact, contactScene);
    }

    public void openTasks(ActionEvent event) throws IOException{
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/tasksWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfContact, contactScene);
    }

    public void openNotes(ActionEvent event) throws IOException{
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/notesWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfContact, contactScene);
    }
}
