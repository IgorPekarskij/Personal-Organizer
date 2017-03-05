package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleIntegerProperty personID = new SimpleIntegerProperty();
    private SimpleStringProperty surname = new SimpleStringProperty("");
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty middleName = new SimpleStringProperty("");
    private SimpleStringProperty phoneNumber = new SimpleStringProperty("");
    private SimpleStringProperty email = new SimpleStringProperty("");
    private SimpleStringProperty country = new SimpleStringProperty("");
    private SimpleStringProperty city = new SimpleStringProperty("");
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty birthday = new SimpleStringProperty("");
    private SimpleStringProperty personNote = new SimpleStringProperty("");
    private SimpleObjectProperty<byte[]> personImage = new SimpleObjectProperty<>();

    public Contact() {
    }

    public Contact(int id, String surname, String name, String middleName, String phoneNumber, String email, String country, String city, String address, String birthday, String personNote, byte[] image) {
        this.personID = new SimpleIntegerProperty(id);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.middleName = new SimpleStringProperty(middleName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.country = new SimpleStringProperty(country);
        this.city = new SimpleStringProperty(city);
        this.address = new SimpleStringProperty(address);
        this.birthday = new SimpleStringProperty(birthday);
        this.personNote = new SimpleStringProperty(personNote);
        this.personImage = new SimpleObjectProperty(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (personID != null ? !personID.equals(contact.personID) : contact.personID != null) return false;
        if (surname != null ? !surname.equals(contact.surname) : contact.surname != null) return false;
        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;
        if (middleName != null ? !middleName.equals(contact.middleName) : contact.middleName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(contact.phoneNumber) : contact.phoneNumber != null) return false;
        if (email != null ? !email.equals(contact.email) : contact.email != null) return false;
        if (address != null ? !address.equals(contact.address) : contact.address != null) return false;
        if (birthday != null ? !birthday.equals(contact.birthday) : contact.birthday != null) return false;
        if (personNote != null ? !personNote.equals(contact.personNote) : contact.personNote != null) return false;
        return personImage != null ? personImage.equals(contact.personImage) : contact.personImage == null;
    }

    @Override
    public int hashCode() {
        int result = personID != null ? personID.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (personNote != null ? personNote.hashCode() : 0);
        result = 31 * result + (personImage != null ? personImage.hashCode() : 0);
        return result;
    }

    public int getPersonID() {

        return personID.get();
    }

    public SimpleIntegerProperty personIDProperty() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID.set(personID);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
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

    public String getMiddleName() {
        return middleName.get();
    }

    public SimpleStringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public SimpleStringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getPersonNote() {
        return personNote.get();
    }

    public SimpleStringProperty personNoteProperty() {
        return personNote;
    }

    public void setPersonNote(String personNote) {
        this.personNote.set(personNote);
    }

    public byte[] getPersonImage() {
        return personImage.get();
    }

    public SimpleObjectProperty<byte[]> personImageProperty() {
        return personImage;
    }

    public void setPersonImage(byte[] personImage) {
        this.personImage.set(personImage);
    }
}
