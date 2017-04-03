package controllers;

import interfaces.impls.CollectionContacts;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import objects.Task;
import utils.CalendarGenerator;
import utils.ConvertData;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskWindowController {

    private CollectionTasks collectionTasks = new CollectionTasks();
    private ContactsWindowController contactsController = new ContactsWindowController();
    private CreateNewTaskController createNewTask = new CreateNewTaskController();
    private Parent fxmlEdit;
    private Stage editDialogStage;
    private String confirmDeleteTitle = "Удаление задачи!";
    private String confirmDeleteMessage = "Вы действительно хотите удалить задачу?";
    private String chooseTaskMessage = "Выберите задачу!";
    private String exportTaskTitle = "Экспорт задач";
    private String importTaskTitle = "Импортирование задач";
    private String filesWithExtension = "*.ics";
    private String fileExtension = ".ics";
    private FXMLLoader fxmlLoader;
    public static boolean addTask;
    public static boolean newTask = true;
    @FXML
    private Label taskLoadLabel;
    @FXML
    private ImageView taskSpinner;
    @FXML
    private MenuItem exportSelectedTask;
    @FXML
    private MenuItem importTasks;
    @FXML
    private MenuItem exportTasks;
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

                if (newValue.compareTo(ConvertData.convertStringToLocalDate(task.getStartDate())) >= 0 &&
                    newValue.compareTo(ConvertData.convertStringToLocalDate(task.getEndDate())) <= 0) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Task> sortedList = new SortedList<Task>(filteredList);
        sortedList.comparatorProperty().bind(listOfTasks.comparatorProperty());
        listOfTasks.setItems(sortedList);
        updateCountLabel();
        exportTask();
        exportSelectedTask();
        importTask();
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
        countSelectedItemLabel.textProperty().bind(Bindings.size(listOfTasks.getItems()).asString(ContactsWindowController.getFoundedRecords()));
    }

    public static void setAddTask(boolean addTask) {
        TaskWindowController.addTask = addTask;
    }

    public void setCompleted(ActionEvent actionEvent) {
        int rowNumber = listOfTasks.getSelectionModel().getSelectedIndex();
        if (rowNumber < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText(chooseTaskMessage);
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
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText(chooseTaskMessage);
            alert.showAndWait();
        } else {
            ButtonType ok = new ButtonType(ContactsWindowController.getConfirmButtonLabel(), ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType(ContactsWindowController.getDeclineButtonLabel(), ButtonBar.ButtonData.NO);
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, confirmDeleteMessage, ok, no);
            confirmDelete.setTitle(confirmDeleteTitle);
            confirmDelete.showAndWait();
            if (confirmDelete.getResult() == ok) {
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


    public void importTask() {

        importTasks.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                taskLoadLabel.setText(importTaskTitle);
                taskSpinner.setImage(new Image(CollectionContacts.class.getResource("/pictures/spinner.gif").toExternalForm()));
                FileInputStream fin = null;
                CalendarBuilder builder = new CalendarBuilder();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(importTaskTitle);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ics files (" + filesWithExtension +  ")", filesWithExtension);
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(listOfTasks.getScene().getWindow());
                if (file != null) {
                    try {
                        fin = new FileInputStream(file);
                        Calendar calendar = builder.build(fin);
                        List<List<Task>> tasks = CalendarGenerator.createTasksFromIcal(calendar);
                        CollectionTasks.addTask(tasks);
                        taskLoadLabel.setText(ContactsWindowController.getEmptyString());
                        taskSpinner.setImage(null);
                    } catch (ParserException e) {
                        e.printStackTrace();
                    } catch (RuntimeException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(ContactsWindowController.getErrorLoadAlertTitle());
                        alert.getDialogPane().setPrefWidth(500);
                        alert.setHeaderText(ContactsWindowController.getErrorLoadAlertMessage());
                        alert.showAndWait();
                        ex.printStackTrace();
                    } catch (IOException e) {

                    }
                } else {
                    taskLoadLabel.setText(ContactsWindowController.getEmptyString());
                    taskSpinner.setImage(null);
                }
            }
        });
    }

    public void exportTask() {
        exportTasks.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(exportTaskTitle);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ics files (" + filesWithExtension +  ")", filesWithExtension);
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(listOfTasks.getScene().getWindow());
                if (file != null) {
                    Calendar calendar = CalendarGenerator.generateCalendar(CollectionTasks.getTaskList());
                    if (!file.getName().endsWith(fileExtension)) {
                        file = new File(file.getAbsolutePath() + fileExtension);
                    }
                    try {
                        FileOutputStream fout = new FileOutputStream(file);
                        CalendarOutputter outputter = new CalendarOutputter();
                        outputter.setValidating(false);
                        outputter.output(calendar, fout);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }

    public void exportSelectedTask() {
        exportSelectedTask.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(exportTaskTitle);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ics files (" + filesWithExtension +  ")", filesWithExtension);
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(listOfTasks.getScene().getWindow());
                if (file != null) {
                    List<Task> task = new ArrayList<>();
                    task.add((Task) listOfTasks.getSelectionModel().getSelectedItem());
                    Calendar calendar = CalendarGenerator.generateCalendar(task);
                    if (!file.getName().endsWith(fileExtension)) {
                        file = new File(file.getAbsolutePath() + fileExtension);
                    }
                    try {
                        FileOutputStream fout = new FileOutputStream(file);
                        CalendarOutputter outputter = new CalendarOutputter();
                        outputter.setValidating(false);
                        outputter.output(calendar, fout);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }
}
