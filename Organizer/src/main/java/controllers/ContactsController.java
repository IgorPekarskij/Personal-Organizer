package controllers;

import interfaces.impls.CollectionContacts;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.Person;

import java.io.IOException;

public class ContactsController {
    CollectionContacts collectionContacts = new CollectionContacts();
    @FXML
    public TableView fullListOfContact;
    @FXML
    public TableColumn fioColumn;
    @FXML
    public TableColumn phoneColumn;
    @FXML
    public Label countFoundRecords;
    @FXML
    private void initialize(){
        fioColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("FIO"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("PhoneNumber"));
        collectionContacts.getPersonsList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateCountLabel();
            }
        });
        collectionContacts.fillList();
        fullListOfContact.setItems(collectionContacts.getPersonsList());
    }

    public void createNewContact(ActionEvent event) throws IOException {
        WelcomeWindowController createContact = new WelcomeWindowController();
        createContact.openNewContact(event);
    }

    public void deleteContact(ActionEvent event) {
        int selectedIndex = fullListOfContact.getSelectionModel().getSelectedIndex();
        CollectionContacts.deleteContact(selectedIndex);
    }

    private void updateCountLabel(){
        countFoundRecords.setText("Найдено " + collectionContacts.getPersonsList().size() + " записей");
    }

}
