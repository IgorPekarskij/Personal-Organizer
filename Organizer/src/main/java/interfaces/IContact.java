package interfaces;

import objects.Contact;
import java.util.List;

public interface IContact {
    static int addContact (Contact person){return -1;};
    static void updateContact (Contact person){};
    static void deleteContact (Contact person){};
    static void addContacts (List<Contact> contactList) {};
}
