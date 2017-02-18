package controllers;

import interfaces.impls.CollectionTasks;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import objects.Task;
import utils.ConvertData;

import java.io.IOException;

public class TaskWindowController {

    private CollectionTasks collectionTasks = new CollectionTasks();
    private ContactsWindowController contactsController = new ContactsWindowController();
    private CreateNewTaskController createNewTask = new CreateNewTaskController();
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader;
    public static boolean addTask;
    public static boolean newTask = true;
    private Stage editDialogStage;
    @FXML
    private TableColumn completedColumn;
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
    private TableView listOfTasks;

    @FXML
    private void initialize() {
        //Fill table.
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("startDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("startTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("endDate"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("endTime"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        completedColumn.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("completed"));
        initListeners();
        initLoader();
        fillData();
        completedColumn.setCellFactory(new Callback<TableColumn<Task, Boolean>, TableCell<Task, Boolean>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new CheckBoxTableCell<Task, Boolean>() {
                    {
                        setAlignment(Pos.CENTER);
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        if (!empty) {
                            TableRow row = getTableRow();
                            if (row != null) {
                                int rowNo = row.getIndex();
                                TableView.TableViewSelectionModel sm = getTableView().getSelectionModel();
                                if (item) sm.select(rowNo);
                                else sm.clearSelection(rowNo);
                            }
                        }
                        super.updateItem(item, empty);
                    }
                };
            }
        });
        completedColumn.setEditable(true);
        FilteredList<Task> filteredList = new FilteredList<>(collectionTasks.getTaskList(), p -> true);
        searchTaskTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(task -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (task.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (task.getStartDate().contains(lowerCaseFilter)) {
                    return true;
                } else if (task.getStartTime().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        }));
        selectTaskFromDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(task -> {
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
        SortedList<Task> sortedList = new SortedList<Task>(filteredList);
        sortedList.comparatorProperty().bind(listOfTasks.comparatorProperty());
        listOfTasks.setItems(sortedList);
        updateCountLabel();
    }

    private void initListeners() {
        //Open edit window by double click on current contact
        listOfTasks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    newTask = false;
                    createNewTask.setTask((Task) listOfTasks.getSelectionModel().getSelectedItem());
                    editDialogStage = contactsController.showWindow(editDialogStage, listOfTasks, fxmlEdit);
                }
            }
        });
    }

    public void createTask(ActionEvent event) throws IOException {
        Object currentEvent = event.getSource();
        if (!(currentEvent instanceof Button)) {
            return;
        }
        Button currentButton = (Button) currentEvent;
        switch (currentButton.getId()) {
            case "addNewTaskButton":
                newTask = true;
                createNewTask.setTask(new Task());
                editDialogStage = contactsController.showWindow(editDialogStage, listOfTasks, fxmlEdit);
                if (addTask) {
                    int id = CollectionTasks.addTask(createNewTask.getNewTask());
                    createNewTask.getNewTask().setTaskID(id);
                    listOfTasks.getSelectionModel().selectLast();
                }
                break;
            case "editTaskButton":
                newTask = false;
                createNewTask.setTask((Task) listOfTasks.getSelectionModel().getSelectedItem());
                editDialogStage = contactsController.showWindow(editDialogStage, listOfTasks, fxmlEdit);
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
        listOfTasks.setItems(CollectionTasks.getTaskList());
        listOfTasks.getSelectionModel().selectFirst();
    }

    public void openContacts(ActionEvent event) throws IOException {
        Parent taskScene = FXMLLoader.load(getClass().getResource("/fxmls/contactsWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfTasks, taskScene);
    }

    public void openNotes(ActionEvent event) throws IOException {
        Parent taskScene = FXMLLoader.load(getClass().getResource("/fxmls/notesWindow.fxml"));
        WelcomeWindowController.openNewWindow(listOfTasks, taskScene);
    }

    public void exitApplication(ActionEvent actionEvent) {
        LoginWindowController.exitApplication();
    }

    private void updateCountLabel() {
        countSelectedItemLabel.textProperty().bind(Bindings.size(listOfTasks.getItems()).asString("Найдено %s записей"));
    }

    public static void setAddTask(boolean addTask) {
        TaskWindowController.addTask = addTask;
    }

    public void setCompleted(ActionEvent actionEvent) {
        int rowNumber = listOfTasks.getSelectionModel().getSelectedIndex();
        if (rowNumber < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Органайзер");
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText("Выберите задачу!");
            alert.showAndWait();
        } else {
            Task currentTask = (Task) listOfTasks.getSelectionModel().getSelectedItem();
            currentTask.setCompleted(currentTask.isCompleted() == true ? false : true);
            listOfTasks.getSelectionModel().select(rowNumber, taskNameColumn);
            CollectionTasks.updateTask(currentTask);
        }
    }

    public void deleteTask(ActionEvent actionEvent) {
        int rowNumber = listOfTasks.getSelectionModel().getSelectedIndex();
        if (rowNumber < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Органайзер");
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText("Выберите задачу!");
            alert.showAndWait();
        } else {
            ButtonType ok = new ButtonType("Да", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("Нет", ButtonBar.ButtonData.NO);
            Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION, "Вы действительно хотите удалить задачу?", ok, no);
            confirmExit.setHeaderText("");
            confirmExit.setTitle("Удаление задачи!");
            confirmExit.showAndWait();
            if (confirmExit.getResult() == ok) {
                CollectionTasks.deleteTask((Task) listOfTasks.getSelectionModel().getSelectedItem());
            }
        }

    }

    public void toWelcome(ActionEvent actionEvent) throws IOException {
        Parent welcomeScene = FXMLLoader.load(getClass().getResource("/fxmls/welcomeWindow.fxml"));
        LoginWindowController.openWelcomeWindow(welcomeScene, listOfTasks);
    }

    public void changeUser(ActionEvent actionEvent) throws IOException {
        Parent registerUser = FXMLLoader.load(getClass().getResource("/fxmls/registartionForm.fxml"));
        LoginWindowController.changeUserWindow(registerUser, listOfTasks);
    }

    public static boolean isNewTask() {
        return newTask;
    }
}
