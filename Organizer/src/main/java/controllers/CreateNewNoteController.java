package controllers;

import interfaces.impls.CollectionNotes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import objects.Note;
import utils.ConvertData;


public class CreateNewNoteController {
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

    public CreateNewNoteController() {
        newContactController = new CreateNewContactController();
        notesWindowController = new NotesWindowController();
    }

    @FXML
    private void initialise(){
    }
    public Note getNewNote() {
        return newNote;
    }

    public void setNewNote(Note note) {
    }

    public void setNote(Note note){
        if(note != null){
            this.newNote = note;
            newNoteNameField.setText(newNote.getName());
            newNoteDetailTextArea.setText(newNote.getDescription());
            newNoteDate.setValue(ConvertData.convertStringToLocalDate(newNote.getNoteDate()));
            newNoteAttacheImageView.setImage(ConvertData.convertToImage(newNote.getNoteImage()));
        }
    }

    public void closeNewNoteWindow(ActionEvent actionEvent) {
        NotesWindowController.setAddNote(false);
        newContactController.hideWindow(cancelNewNoteButton);
        System.out.println(notesWindowController.isAddNote());
    }

    public void saveNewNote(ActionEvent actionEvent) {
        //Доработать вставку первой записи
        System.out.println(newNote);
        this.newNote.setNoteID(CollectionNotes.getNotesList().get(CollectionNotes.getNotesList().size()-1).getNoteID()+1);

        this.newNote.setName(newNoteNameField.getText() == null ? " " : newNoteNameField.getText());
        this.newNote.setDescription(newNoteDetailTextArea.getText() == null ? " " : newNoteDetailTextArea.getText());
        this.newNote.setNoteDate(newNoteDate.getValue() == null ? " " : ConvertData.convertLocalDateToString(newNoteDate.getValue()));
        notesWindowController.setAddNote(true);
        newContactController.hideWindow(saveNewNoteButton);
    }

    public void clearNewNoteFields(ActionEvent actionEvent) {
    }

    public void addFileToNotes(ActionEvent actionEvent) {
    }
}
