package interfaces;

import objects.Person;

public interface IContact {
    void addContact (Person person);
    void updateContact (Person person);
    void deleteContact (Person person);
}
