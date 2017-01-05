package controllers;

import interfaces.impls.CollectionTasks;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Task;
import utils.ConvertData;

import java.io.IOException;

public class TaskWindowController {

    private CollectionTasks collectionTasks = new CollectionTasks();
    private ContactsController contactsController = new ContactsController();
    private CreateNewTaskController createNewTask = new CreateNewTaskController();
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader;
    public static boolean addTask;
    private boolean mark = true;
    private Stage editDialogStage;
    @FXML
    private DatePicker selectTaskFromDatePicker;
    @FXML
    private TextField searchTaskTextField;
    @FXML
    private Label countSelectedItemLabel;
    @FXML
    private TableColumn startDateColumn;
    @FXML
    private TableColumn startTimeColumn;
    @FXML
    private TableColumn endDateColumn;
    @FXML
    private TableColumn endTimeColumn;
    @FXML
    private TableColumn taskNameColumn;
    @FXML
    private TableView taskTableView;

    @FXML
    private void initialize() {
        //Fill table.
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("startDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("startTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("endDate"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("endTime"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        fillData();
        initLoader();
        initListeners();
        FilteredList<Task> filteredList = new FilteredList<>(collectionTasks.getTaskList(), p -> true);
        searchTaskTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(task -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (task.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (task.getStartDate().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (task.getStartTime().contains(lowerCaseFilter)) {
                    return true; // Filter matches phone.
                }
                return false; // Does not match.
            });
        }) );
        SortedList<Task> sortedList = new SortedList<Task>(filteredList);
        sortedList.comparatorProperty().bind(taskTableView.comparatorProperty());
        taskTableView.setItems(sortedList);
        FilteredList<Task> filtered = new FilteredList<>(collectionTasks.getTaskList(), p -> true);
        selectTaskFromDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            filtered.setPredicate(task -> {
                if (newValue == null) {
                    return true;
                }
                String dateToString = ConvertData.convertLocalDateToString(newValue);
                if (task.getStartDate().contains(dateToString)) {
                    return true;
                }
                if (task.getEndDate().contains(dateToString)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Task> sortedSecList = new SortedList<Task>(filtered);
        sortedList.comparatorProperty().bind(taskTableView.comparatorProperty());
        taskTableView.setItems(filtered);
        updateCountLabel();
    }

    private void initListeners() {
        //Open edit window by double click on current contact
        taskTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    createNewTask.setTask((Task) taskTableView.getSelectionModel().getSelectedItem());
                    editDialogStage = contactsController.showWindow(editDialogStage, taskTableView, fxmlEdit);
                }
            }
        });
    }

    public void createTask(ActionEvent event) throws IOException {
        Object currentEvent = event.getSource();
        if(!(currentEvent instanceof Button)){
            return;
        }
        Button currentButton = (Button) currentEvent;
        switch (currentButton.getId()) {
            case "addNewTaskButton" :
                createNewTask.setTask(new Task());
                editDialogStage = contactsController.showWindow(editDialogStage, taskTableView, fxmlEdit);
                if (addTask) {
                    CollectionTasks.addTask(createNewTask.getNewTask());
                    taskTableView.getSelectionModel().selectLast();
                }
                break;
            case "editTaskButton" :
                createNewTask.setTask((Task) taskTableView.getSelectionModel().getSelectedItem());
                editDialogStage = contactsController.showWindow(editDialogStage, taskTableView, fxmlEdit);
                mark = ((Task) taskTableView.getSelectionModel().getSelectedItem()).isIsImportant();
                //markAsImportant(mark, taskTableView.getSelectionModel().getFocusedIndex());
                break;
        }
    }
    private void initLoader() {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxmls/newTaskWindow.fxml"));
            fxmlEdit = fxmlLoader.load();
            createNewTask = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillData() {
        collectionTasks.fillTaskList();
        taskTableView.setItems(collectionTasks.getTaskList());
        taskTableView.getSelectionModel().selectFirst();
    }
    public void openContacts(ActionEvent event) throws IOException {
        Parent taskScene = FXMLLoader.load(getClass().getResource("/fxmls/contactsWindow.fxml"));
        WelcomeWindowController.openNewWindow(taskTableView, taskScene);
    }

    public void openTasks(ActionEvent event) throws IOException {
        Parent taskScene = FXMLLoader.load(getClass().getResource("/fxmls/tasksWindow.fxml"));
        WelcomeWindowController.openNewWindow(taskTableView, taskScene);
    }

    public void openNotes(ActionEvent event) throws IOException {
        Parent taskScene = FXMLLoader.load(getClass().getResource("/fxmls/notesWindow.fxml"));
        WelcomeWindowController.openNewWindow(taskTableView, taskScene);
    }

    public void exitApplication(ActionEvent actionEvent) {
        LoginWindowController.exitApplication();
    }
    private void updateCountLabel(){
        countSelectedItemLabel.textProperty().bind(Bindings.size(taskTableView.getItems()).asString("Найдено %s записей"));
    }

    public static boolean isAddTask() {
        return addTask;
    }

    public static void setAddTask(boolean addTask) {
        TaskWindowController.addTask = addTask;
    }

    public void setCompleted(ActionEvent actionEvent) {
        createNewTask.markAsCompleted();
    }

    public void deleteTask(ActionEvent actionEvent) {
        CollectionTasks.deleteTask((Task) taskTableView.getSelectionModel().getSelectedItem());
    }

    public void toWelcome(ActionEvent actionEvent) throws IOException {
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/welcomeWindow.fxml"));
        LoginWindowController.openWelcomeWindow(welcomeScene, taskTableView);
    }

    public void changeUser(ActionEvent actionEvent) throws IOException {
        Parent registerUser = FXMLLoader.load(getClass().getResource("/fxmls/registartionForm.fxml"));
        LoginWindowController.changeUserWindow(registerUser, taskTableView);
    }
}
