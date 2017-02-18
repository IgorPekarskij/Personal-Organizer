package interfaces;

import objects.Contact;

public interface IContact {
    static int addContact (Contact person){return -1;};
    static void updateContact (Contact person){};
    static void deleteContact (Contact person){};
}
