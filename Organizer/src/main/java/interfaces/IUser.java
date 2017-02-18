package interfaces;

import objects.User;

public interface IUser {
    static void addUser (User user){}
    void updateUser ();
    static void deleteUser (User user){}
}
