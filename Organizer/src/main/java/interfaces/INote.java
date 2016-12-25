package interfaces;

import objects.Note;

public interface INote {
    static void addNote (Note note){};
    void updateNote (Note note);
    static void deleteNote (Note note){};
}
