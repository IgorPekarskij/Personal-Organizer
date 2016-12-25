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
import java.io.IOException;

public class NotesWindowController {

    private ContactsController contactsController = new ContactsController();
    private CollectionNotes collectionNotes = new CollectionNotes();
    private FXMLLoader fxmlLoader;
    private Parent fxmlEdit;
    private CreateNewNoteController createNewNote;
    private Stage editNoteStage;
    private static boolean addNote = true;
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
          //listOfNotes.setItems(collectionContact.getNotesList());
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
                    return true; // Filter matches first name.
                }else if (note.getNoteDate().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        }) );

        SortedList<Note> sortedList = new SortedList<>(filteredNoteList);
        sortedList.comparatorProperty().bind(listOfNotes.comparatorProperty());
        listOfNotes.setItems(sortedList);
        updateCountLabel();
    }

    private void fillData() {
        collectionNotes.fillNoteList();
        listOfNotes.setItems(CollectionNotes.getNotesList());
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
                if(event.getClickCount() == 2) {
                    createNewNote.setNote((Note) listOfNotes.getSelectionModel().getSelectedItem());
                    editNoteStage = contactsController.showWindow(editNoteStage, listOfNotes, fxmlEdit);
                }
            }
        });
    }

    public void createNewNote(ActionEvent event) throws IOException {
        Object currentEvent = event.getSource();
        if(!(currentEvent instanceof Button)){
            return;
        }
        Button currentButton = (Button) currentEvent;
        switch (currentButton.getId()) {
            case "addNewNoteButton" :
                System.out.println(addNote);
                createNewNote.setNote(new Note());
                editNoteStage = contactsController.showWindow(editNoteStage, listOfNotes, fxmlEdit);
                System.out.println(addNote);
                if (addNote) {
                    CollectionNotes.addNote(createNewNote.getNewNote());
                    listOfNotes.getSelectionModel().selectLast();
                }
                break;
            case "editNoteButton" :
                createNewNote.setNote((Note) listOfNotes.getSelectionModel().getSelectedItem());
                editNoteStage = contactsController.showWindow(editNoteStage, listOfNotes, fxmlEdit);
                break;
        }
    }
    public void exitApplication(ActionEvent event) throws IOException {
        LoginWindowController.exitApplication();
    }

    public void openContacts(ActionEvent event) throws IOException{
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/contactsWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfNotes, contactScene);
    }

    public void openTasks(ActionEvent event) throws IOException {
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/tasksWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfNotes, contactScene);
    }

    public void openNotes(ActionEvent event) throws IOException {
        Parent contactScene = FXMLLoader.load(getClass().getResource("/fxmls/notesWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfNotes, contactScene);
    }

    public void openNewNoteWindow(ActionEvent event) throws IOException{
        createNewNote(event);
    }

    public void deleteNote(ActionEvent event) {
        CollectionNotes.deleteNote((Note) listOfNotes.getSelectionModel().getSelectedItem());
    }

    private void updateCountLabel(){
        countNotes.textProperty().bind(Bindings.size(listOfNotes.getItems()).asString("Найдено %s записей"));
    }

    public boolean isAddNote() {
        return addNote;
    }

    public static void setAddNote(boolean addNote) {
        NotesWindowController.addNote = addNote;
    }
}
