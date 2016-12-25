package interfaces.impls;

import interfaces.IContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Note;
import objects.Person;
import testData.TestData;

public class CollectionContacts implements IContact {
    private static ObservableList<Person> personsList = FXCollections.observableArrayList();

    public static void addContact(Person person) {
        personsList.add(person);
    }

    public void updateContact(Person person) {

    }

    public static void deleteContact(Person person) {
        personsList.remove(person);
    }

    public void fillContactList(){
        if (personsList.size() == 0) {
            personsList = TestData.createContacts();
        }
        System.out.println(personsList.size());
    }

    public void setNotesList(ObservableList<Note> notesList) {
        CollectionNotes.notesList = notesList;
    }

    public static ObservableList<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(ObservableList<Person> personsList) {
        this.personsList = personsList;
    }
}
