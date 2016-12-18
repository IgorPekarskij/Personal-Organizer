package objects;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private int personID;
    private SimpleStringProperty surname = new SimpleStringProperty("");
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty middleName = new SimpleStringProperty("");
    private SimpleStringProperty phoneNumber = new SimpleStringProperty("");
    private SimpleStringProperty email = new SimpleStringProperty("");
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty birthday = new SimpleStringProperty("");
    private SimpleStringProperty personNote = new SimpleStringProperty("");
    private SimpleObjectProperty personImage = new SimpleObjectProperty(new byte[0]);
    public Person() {
    }

    public Person(int id, String surname, String name, String middleName, String phoneNumber, String email, String address, String birthday, String personNote) {
        this.personID = id;
        this.surname = new SimpleStringProperty (surname);
        this.name = new SimpleStringProperty (name);
        this.middleName = new SimpleStringProperty (middleName);
        this.phoneNumber = new SimpleStringProperty (phoneNumber);
        this.email = new SimpleStringProperty (email);
        this.address = new SimpleStringProperty (address);
        this.birthday = new SimpleStringProperty (birthday);
        this.personNote = new SimpleStringProperty (personNote);
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getPersonNote() {
        return personNote.get();
    }

    public void setPersonNote(String personNote) {
        this.personNote.set(personNote);
    }

    public Object getPersonImage() {
        return personImage.get();
    }

    public SimpleObjectProperty personImageProperty() {
        return personImage;
    }

    public void setPersonImage(Object personImage) {
        if (personImage instanceof byte[]) {
            this.personImage.set(personImage);
        }
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty middleNameProperty() {
        return middleName;
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleStringProperty birthdayProperty() {
        return birthday;
    }

    public SimpleStringProperty personNoteProperty() {
        return personNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) { return false; }
        Person person = (Person) o;
        if (personID != person.personID) return false;
        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (middleName != null ? !middleName.equals(person.middleName) : person.middleName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(person.phoneNumber) : person.phoneNumber != null) return false;
        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        if (address != null ? !address.equals(person.address) : person.address != null) return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) return false;
        return personNote != null ? personNote.equals(person.personNote) : person.personNote == null;
    }

    @Override
    public int hashCode() {
        int result = personID;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (personNote != null ? personNote.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "surname=" + surname +
                ", name=" + name +
                ", middleName=" + middleName +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", address=" + address +
                ", birthday=" + birthday +
                ", personNote=" + personNote +
                '}';
    }
}
