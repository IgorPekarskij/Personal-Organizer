package start;

import controllers.LoginWindowController;
import interfaces.impls.CollectionContacts;
import interfaces.impls.CollectionNotes;
import interfaces.impls.CollectionTasks;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Connections;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CollectionContacts collectionContacts = new CollectionContacts();
        collectionContacts.fillContactList();
        CollectionNotes collectionNotes = new CollectionNotes();
        collectionNotes.fillNoteList();
        CollectionTasks collectionTasks = new CollectionTasks();
        collectionTasks.fillTaskList();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/loginWindow.fxml"));
        primaryStage.setTitle("Органайзер");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 500, 220));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                LoginWindowController.exitApplication();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        Connections.closeConnection();
        super.stop();
    }
}
