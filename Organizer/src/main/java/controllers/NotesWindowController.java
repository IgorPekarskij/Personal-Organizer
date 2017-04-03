package controllers;

import interfaces.impls.CollectionNotes;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Note;
import utils.ConvertData;

import java.io.IOException;

public class NotesWindowController {
    private ContactsWindowController contactsController = new ContactsWindowController();
    private FXMLLoader fxmlLoader;
    private Parent fxmlEdit;
    private CreateNewNoteController createNewNote;
    private Stage editNoteStage;
    private static boolean addNote = true;
    private String confirmDeleteTitle = "Удаление заметки!";
    private String confirmDeleteMessage = "Удаление заметки!";
    private String errorDeleteTitle = "Выберите заметку!";
    @FXML
    private DatePicker selectNoteFromDatePicker;
    @FXML
    private TextField searchNoteTextField;
    @FXML
    private Label countNotes;
    @FXML
    private TableView listOfNotes;
    @FXML
    private TableColumn dataNoteColumn;
    @FXML
    private TableColumn nameNoteColumn;

    @FXML
    private void initialize() {
        dataNoteColumn.setCellValueFactory(new PropertyValueFactory<Note, String>("noteDate"));
        nameNoteColumn.setCellValueFactory(new PropertyValueFactory<Note, String>("name"));
        initListeners();
        initLoader();
        fillData();
        FilteredList<Note> filteredNoteList = new FilteredList<>(CollectionNotes.getNotesList(), p -> true);
        searchNoteTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredNoteList.setPredicate(note -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (note.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (note.getNoteDate().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        }));
        selectNoteFromDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredNoteList.setPredicate(task -> {
                if (newValue == null) {
                    return true;
                }
                String dateToString = ConvertData.convertLocalDateToString(newValue);
                if (task.getNoteDate().contains(dateToString)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Note> sortedList = new SortedList<>(filteredNoteList);
        sortedList.comparatorProperty().bind(listOfNotes.comparatorProperty());
        listOfNotes.setItems(sortedList);
        updateCountLabel();
    }

    private void fillData() {
        listOfNotes.setItems(CollectionNotes.getNotesList());
        listOfNotes.getSelectionModel().selectFirst();
    }

    private void initLoader() {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxmls/newNoteWindow.fxml"));
            fxmlEdit = fxmlLoader.load();
            createNewNote = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListeners() {
        listOfNotes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    createNewNote.setNote((Note) listOfNotes.getSelectionModel().getSelectedItem());
                    editNoteStage = contactsController.showWindow(editNoteStage, listOfNotes, fxmlEdit);
                }
            }
        });
    }

    public void createNewNote(ActionEvent event) throws IOException {
        Object currentEvent = event.getSource();
        if (!(currentEvent instanceof Button)) {
            return;
        }
        Button currentButton = (Button) currentEvent;
        switch (currentButton.getId()) {
            case "addNewNoteButton":
                addNote = true;
                createNewNote.setNote(new Note());
                editNoteStage = contactsController.showWindow(editNoteStage, listOfNotes, fxmlEdit);
                if (addNote) {
                    int id = CollectionNotes.addNote(createNewNote.getNewNote());
                    createNewNote.getNewNote().setNoteID(id);
                    listOfNotes.getSelectionModel().selectLast();
                }
                break;
            case "editNoteButton":
                createNewNote.setNote((Note) listOfNotes.getSelectionModel().getSelectedItem());
                editNoteStage = contactsController.showWindow(editNoteStage, listOfNotes, fxmlEdit);
                break;
        }
    }

    public void exitApplication(ActionEvent event) throws IOException {
        LoginWindowController.exitApplication();
    }

    public void openContacts(ActionEvent event) throws IOException {
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/contactsWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfNotes, contactScene);
    }

    public void openTasks(ActionEvent event) throws IOException {
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/tasksWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfNotes, contactScene);
    }

    public void deleteNote(ActionEvent event) {
        int rowNumber = listOfNotes.getSelectionModel().getSelectedIndex();
        if (rowNumber < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(LoginWindowController.getTitle());
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText(errorDeleteTitle);
            alert.showAndWait();
        } else {
            ButtonType ok = new ButtonType(ContactsWindowController.getConfirmButtonLabel(), ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType(ContactsWindowController.getDeclineButtonLabel(), ButtonBar.ButtonData.NO);
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, confirmDeleteMessage, ok, no);
            confirmDelete.setHeaderText("");
            confirmDelete.setTitle(confirmDeleteTitle);
            confirmDelete.showAndWait();
            if (confirmDelete.getResult() == ok) {
                CollectionNotes.deleteNote((Note) listOfNotes.getSelectionModel().getSelectedItem());
            }
        }
    }

    private void updateCountLabel() {
        countNotes.textProperty().bind(Bindings.size(listOfNotes.getItems()).asString(ContactsWindowController.getFoundedRecords()));
    }

    public static void setAddNote(boolean addNote) {
        NotesWindowController.addNote = addNote;
    }

    public void toWelcome(ActionEvent actionEvent) throws IOException {
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/welcomeWindow.fxml"));
        LoginWindowController.openWelcomeWindow(welcomeScene, listOfNotes);
    }

    public void changeUser(ActionEvent actionEvent) throws IOException {
        Parent registerUser = FXMLLoader.load(getClass().getResource("/fxmls/registartionForm.fxml"));
        LoginWindowController.changeUserWindow(registerUser, listOfNotes);
    }
}
