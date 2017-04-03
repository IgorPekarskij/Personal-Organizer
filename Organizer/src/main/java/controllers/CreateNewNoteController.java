package controllers;

import interfaces.impls.CollectionNotes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import objects.Note;
import utils.ConvertData;

import java.time.LocalDate;

public class CreateNewNoteController {
    @FXML
    private Button attacheFileNoteButton;
    @FXML
    private Button saveNewNoteButton;
    @FXML
    private Button cancelNewNoteButton;
    @FXML
    private TextArea newNoteDetailTextArea;
    @FXML
    private TextField newNoteNameField;
    @FXML
    private DatePicker newNoteDate;
    @FXML
    private ImageView newNoteAttacheImageView;
    private Note newNote;
    private CreateNewContactController newContactController;
    private NotesWindowController notesWindowController;
    private String errorSaveAlertHeader = "Заполните фамилию заметки!";
    private String getErrorSaveAlertMessage = "Имя заметки не может быть пустым!";

    @FXML
    private void initialize() {
        addFileToNotes();
    }

    public CreateNewNoteController() {
        newContactController = new CreateNewContactController();
        notesWindowController = new NotesWindowController();
    }

    public Note getNewNote() {
        return newNote;
    }

    public void setNote(Note note) {
        if (note != null) {
            this.newNote = note;
            newNoteNameField.setText(newNote.getName());
            newNoteDetailTextArea.setText(newNote.getDescription());
            newNoteDate.setValue(ConvertData.convertStringToLocalDate(newNote.getNoteDate()) == null ? LocalDate.now() :
                    ConvertData.convertStringToLocalDate(newNote.getNoteDate()));
            newNoteAttacheImageView.setImage(ConvertData.convertToImage(newNote.getNoteImage()));
        }
    }

    public void closeNewNoteWindow(ActionEvent actionEvent) {
        NotesWindowController.setAddNote(false);
        clearNewNoteFields(actionEvent);
        newContactController.hideWindow(cancelNewNoteButton);
    }

    public void saveNewNote(ActionEvent actionEvent) {
        if (!newNoteNameField.getText().isEmpty()) {
            this.newNote.setName(newNoteNameField.getText());
            this.newNote.setDescription(newNoteDetailTextArea.getText() == null ? " " : newNoteDetailTextArea.getText());
            this.newNote.setNoteDate(newNoteDate.getValue() == null ? " " : ConvertData.convertLocalDateToString(newNoteDate.getValue()));
            this.newNote.setNoteImage(newNoteAttacheImageView.getImage() == null ? new byte[0] : newContactController.getTempImage());
            notesWindowController.setAddNote(true);
            newContactController.hideWindow(saveNewNoteButton);
            CollectionNotes.updateNote(newNote);
            clearNewNoteFields(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(LoginWindowController.getTitle());
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText(errorSaveAlertHeader);
            alert.setContentText(getErrorSaveAlertMessage);
            alert.showAndWait();
        }
    }

    public void clearNewNoteFields(ActionEvent actionEvent) {
        newNoteAttacheImageView.setImage(null);
        newNoteNameField.setText(ContactsWindowController.getEmptyString());
        newNoteDetailTextArea.setText(ContactsWindowController.getEmptyString());
        newNoteDate.setValue(null);
    }

    public void addFileToNotes() {
        newContactController.chooseImage(attacheFileNoteButton, newNoteAttacheImageView);
    }

    public void exitApplication(ActionEvent actionEvent) {
        LoginWindowController.exitApplication();
    }
}
