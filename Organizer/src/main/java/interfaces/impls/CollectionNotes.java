package interfaces.impls;

import interfaces.INote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Note;
import testData.TestData;

public class CollectionNotes implements INote{

    static ObservableList<Note> notesList = FXCollections.observableArrayList();

    public static void addNote(Note note) {
        notesList.add(note);
    }

    @Override
    public void updateNote(Note note) {

    }

    public static ObservableList<Note> getNotesList() {
        return notesList;
    }

    public static void deleteNote(Note note) {
        notesList.remove(note);
    }

    public void fillNoteList(){
        if (CollectionNotes.notesList.size() == 0) {
            CollectionNotes.notesList = TestData.createNote();
        }
        System.out.println(CollectionNotes.notesList.size());
    }
}
