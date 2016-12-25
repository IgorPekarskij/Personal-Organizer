package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Note {
    private SimpleIntegerProperty noteID = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty description = new SimpleStringProperty("");
    private SimpleStringProperty noteDate = new SimpleStringProperty("");
    private SimpleObjectProperty noteImage = new SimpleObjectProperty(new byte[0]);

    public Note() {
    }

    public Note(int noteId, String name, String description, String noteDate,
                byte[] personImage) {
        this.noteID = new SimpleIntegerProperty(noteId);
        this.name = new SimpleStringProperty (name);
        this.description = new SimpleStringProperty (description);
        this.noteDate = new SimpleStringProperty (noteDate);
        this.noteImage = new SimpleObjectProperty(personImage);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getNoteDate() {
        return noteDate.get();
    }

    public SimpleStringProperty noteDateProperty() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate.set(noteDate);
    }

    public Object getNoteImage() {
        return noteImage.get();
    }

    public SimpleObjectProperty noteImageProperty() {
        return noteImage;
    }

    public void setNoteImage(Object noteImage) {
        this.noteImage.set(noteImage);
    }

    public int getNoteID() {
        return noteID.get();
    }

    public void setNoteID(int noteID) {
        this.noteID.set(noteID);
    }

    public SimpleIntegerProperty noteIDProperty() {
        return noteID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (name != null ? !name.equals(note.name) : note.name != null) return false;
        if (description != null ? !description.equals(note.description) : note.description != null) return false;
        if (noteDate != null ? !noteDate.equals(note.noteDate) : note.noteDate != null) return false;
        return noteImage != null ? noteImage.equals(note.noteImage) : note.noteImage == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (noteDate != null ? noteDate.hashCode() : 0);
        result = 31 * result + (noteImage != null ? noteImage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "name=" + name +
                ", description=" + description +
                ", noteDate=" + noteDate +
                ", noteImage=" + noteImage +
                '}';
    }
}
