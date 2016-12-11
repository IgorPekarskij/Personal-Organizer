package interfaces.impls;

import interfaces.IContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Person;
import testData.TestData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectionContacts implements IContact {
    private String surname;
    private String name;
    private String middlename;
    private String phoneNumber;
    private String email;
    private String address;
    private String birthday;
    private String personNote;
//------------------------------------------------------------
    private ObservableList<Person> personsList;
      public CollectionContacts() {
        personsList = FXCollections.observableArrayList();
    }

    public void addContact(Person person) {
        personsList.add(person);
    }

    public void updateContact(Person person) {

    }

    public void deleteContact(Person person) {
        personsList.remove(person);
    }

    public void fillList(){
        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
        for (int i = 1; i <= 10; i++) {
            surname = "Petrov" + i;
            name = "Ivan" + i;
            middlename = "Ivanovich" + i;
            phoneNumber = "40000" + i;
            email = i + "test@mail.ru";
            address = "Brest, Kirova, " + i;
            birthday = date.format(new Date(System.currentTimeMillis()));
            personNote = "Test" + i;
            personsList.add(new Person(surname, name, middlename, phoneNumber, email, address, birthday, personNote));
        }
    }

    public ObservableList<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(ObservableList<Person> personsList) {
        this.personsList = personsList;
    }
}