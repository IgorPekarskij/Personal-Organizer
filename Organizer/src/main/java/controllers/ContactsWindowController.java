package controllers;

import ezvcard.Ezvcard;
import ezvcard.VCard;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.Contact;
import utils.ConvertData;
import utils.VcardFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactsWindowController {

    private Contact currentPerson;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader;
    private CreateNewContactController createNewContact;
    private Stage editDialogStage;
    private String importContactTitle = "Импортирование контактов";
    private String exportContactTitle = "Экспорт контактов";
    private String confirmationDeleteMessage = "Вы действительно хотите удалить контакт?";
    private String confirmationDeleteTitle = "Удаление контакта!";
    private String filesWithExtension = "*.vcf";
    private String fileExtension = ".vcf";
    private String chooseTaskMessage = "Выберите задачу!";
    private static String confirmButtonLabel = "Да";
    private static String cancelButtonLabel = "Нет";
    private static String emptyString = "";
    private static String errorLoadAlertTitle = "Ошибка загрузки";
    private static String errorLoadAlertMessage = "Во время загрузки произошла ошибка!";
    private static String foundedRecords = "Найдено %s записей";
    private static boolean addPerson = true;
    @FXML
    private ImageView loadContactsSpinner;
    @FXML
    private Label loadContactsLabel;
    @FXML
    private MenuItem expSelectedContact;
    @FXML
    private MenuItem impContacts;
    @FXML
    private MenuItem expContacts;
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
        exportContacts();
        exportSelectedContact();
        importContacts();
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
        addressCurrentContact.setText(currentPerson.getCountry() + ", " + currentPerson.getCity() + ", " + currentPerson.getAddress());
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

    public static String getConfirmButtonLabel() {
        return confirmButtonLabel;
    }

    public static String getCancelButtonLabel() {
        return cancelButtonLabel;
    }

    public static String getEmptyString() {
        return emptyString;
    }

    public static String getErrorLoadAlertTitle() {
        return errorLoadAlertTitle;
    }

    public static String getErrorLoadAlertMessage() {
        return errorLoadAlertMessage;
    }

    public static String getFoundedRecords() {
        return foundedRecords;
    }

    public void deleteContact(ActionEvent event) {
        int rowNumber = listOfContact.getSelectionModel().getSelectedIndex();
        if (rowNumber < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(LoginWindowController.getTitle());
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText(chooseTaskMessage);
            alert.showAndWait();
        } else {
            ButtonType ok = new ButtonType(confirmButtonLabel, ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType(cancelButtonLabel, ButtonBar.ButtonData.NO);
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, confirmationDeleteMessage, ok, no);
            confirmDelete.setHeaderText(emptyString);
            confirmDelete.setTitle(confirmationDeleteTitle);
            confirmDelete.showAndWait();
            if (confirmDelete.getResult() == ok) {
                CollectionContacts.deleteContact((Contact) listOfContact.getSelectionModel().getSelectedItem());
                fioCurrentContact.setText(emptyString);
                telephoneCurrentContact.setText(emptyString);
                emailCurrentContact.setText(emptyString);
                birthdayCurrentContact.setText(emptyString);
                addressCurrentContact.setText(emptyString);
                noteCurrentContact.setText(emptyString);
                contactImage.setImage(null);
            }
            listOfContact.refresh();
            listOfContact.getSelectionModel().selectFirst();
        }
    }

    private void updateCountLabel() {
        countFoundRecords.textProperty().bind(Bindings.size(listOfContact.getItems()).asString(foundedRecords));
    }

    public Stage showWindow(Stage stage, TableView tableView, Parent fxmlEdit) {
        if (stage == null) {
            stage = new Stage();
            stage.setTitle(LoginWindowController.getTitle());
            stage.setResizable(true);
            stage.setScene(new Scene(fxmlEdit));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableView.getParent().getScene().getWindow());
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

    public void importContacts() {
        impContacts.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                loadContactsLabel.setText(importContactTitle);
                loadContactsSpinner.setImage(new Image(CollectionContacts.class.getResource("/pictures/spinner.gif").toExternalForm()));
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(importContactTitle);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("VCF files (" + filesWithExtension + ")", filesWithExtension);
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(listOfContact.getScene().getWindow());
                if (file != null) {
                    try {

                        List<VCard> vcards = Ezvcard.parse(file).all();
                        CollectionContacts.addContact(generateContactsList(vcards));
                        loadContactsLabel.setText(emptyString);
                        loadContactsSpinner.setImage(null);
                    } catch (RuntimeException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(errorLoadAlertTitle);
                        alert.getDialogPane().setPrefWidth(500);
                        alert.setHeaderText(errorLoadAlertMessage);
                        alert.showAndWait();
                        ex.printStackTrace();
                    } catch (IOException e) {

                    }
                } else {
                    loadContactsLabel.setText(emptyString);
                    loadContactsSpinner.setImage(null);
                }
            }
        });

    }

    public void exportContacts() {
        expContacts.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(exportContactTitle);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("VCF files (" + filesWithExtension + ")", filesWithExtension);
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(listOfContact.getScene().getWindow());
                if (file != null) {
                    Collection<VCard> vcards = new ArrayList<VCard>();
                    for (Contact item: CollectionContacts.getPersonsList()) {
                        vcards.add(VcardFactory.createVcard(item));
                    }
                    if (!file.getName().endsWith(fileExtension)) {
                        file = new File(file.getAbsolutePath() + fileExtension);
                    }
                    try {
                        Ezvcard.write(vcards).go(file);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }

    private List<List<Contact>> generateContactsList (List<VCard> cards) {
        List<List<Contact>> contactsList = new ArrayList<>();
        List<Contact> contacts = new ArrayList<>();
        Contact contact = null;
        if (cards.size() <= 1000) {
            for (int i = 0; i < cards.size(); i++) {
                contact = VcardFactory.parseVcard(cards.get(i));
                if (contact != null) {
                    contacts.add(contact);
                }
            }
            contactsList.add(contacts);
        } else {
            int preferredSize = 0;
            for (int j = 0; j < cards.size(); j++) {
                contact = VcardFactory.parseVcard(cards.get(j));
                if (contact != null) {
                    contacts.add(contact);
                }
                if (++preferredSize >= 1000) {
                    contactsList.add(contacts);
                    preferredSize = 0;
                }
            }
        }
        return contactsList;
    }

    public void exportSelectedContact() {
        expSelectedContact.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(exportContactTitle);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("VCF files (" + filesWithExtension + ")", filesWithExtension);
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(listOfContact.getScene().getWindow());
                if (file != null) {
                    VCard vcards = VcardFactory.createVcard((Contact) listOfContact.getSelectionModel().getSelectedItem());
                    if (!file.getName().endsWith(fileExtension)) {
                        file = new File(file.getAbsolutePath() + fileExtension);
                    }
                    try {
                        Ezvcard.write(vcards).go(file);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

    }
}