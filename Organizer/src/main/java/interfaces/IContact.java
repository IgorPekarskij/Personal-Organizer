package interfaces;

import objects.Person;

public interface IContact {
    static void addContact (Person person){};
    void updateContact (Person person);
    static void deleteContact (int person){};
}
