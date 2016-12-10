package objects;

import java.util.Date;

public class Person {
    private String surname;
    private String name;
    private String middlename;
    private String phoneNumber;
    private String email;
    private String address;
    private String birthday;
    private String personNote;

    public Person(String surname, String name, String middlename, String phoneNumber, String email, String address, String birthday, String personNote) {
        this.surname = surname;
        this.name = name;
        this.middlename = middlename;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.personNote = personNote;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPersonNote() {
        return personNote;
    }

    public void setPersonNote(String personNote) {
        this.personNote = personNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
        if (!name.equals(person.name)) return false;
        if (middlename != null ? !middlename.equals(person.middlename) : person.middlename != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(person.phoneNumber) : person.phoneNumber != null) return false;
        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        if (address != null ? !address.equals(person.address) : person.address != null) return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) return false;
        return personNote != null ? personNote.equals(person.personNote) : person.personNote == null;

    }

    @Override
    public int hashCode() {
        int result = surname != null ? surname.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + (middlename != null ? middlename.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (personNote != null ? personNote.hashCode() : 0);
        return result;
    }
}
