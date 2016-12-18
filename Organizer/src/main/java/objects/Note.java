package objects;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Note {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty description = new SimpleStringProperty("");
    private SimpleStringProperty noteDate = new SimpleStringProperty("");
    private SimpleObjectProperty personImage = new SimpleObjectProperty(new byte[0]);

    public Note() {
    }

    public Note(SimpleStringProperty name, SimpleStringProperty description, SimpleStringProperty noteDate, SimpleObjectProperty personImage) {
        this.name = name;
        this.description = description;
        this.noteDate = noteDate;
        this.personImage = personImage;
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

    public Object getPersonImage() {
        return personImage.get();
    }

    public SimpleObjectProperty personImageProperty() {
        return personImage;
    }

    public void setPersonImage(Object personImage) {
        this.personImage.set(personImage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (name != null ? !name.equals(note.name) : note.name != null) return false;
        if (description != null ? !description.equals(note.description) : note.description != null) return false;
        if (noteDate != null ? !noteDate.equals(note.noteDate) : note.noteDate != null) return false;
        return personImage != null ? personImage.equals(note.personImage) : note.personImage == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (noteDate != null ? noteDate.hashCode() : 0);
        result = 31 * result + (personImage != null ? personImage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "name=" + name +
                ", description=" + description +
                ", noteDate=" + noteDate +
                ", personImage=" + personImage +
                '}';
    }
}
