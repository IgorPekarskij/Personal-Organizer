package controllers;

import interfaces.impls.CollectionTasks;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import objects.Task;
import utils.ConvertData;

import java.time.LocalDate;

public class CreateNewTaskController {
    private String hoursDefault = "Часы";
    private String minutesDefault = "Минуты";
    private String hoursDefaultValue = "00";
    private String minutesDefaultValue = "00";
    @FXML
    private CheckBox completedTaskCheckBox;
    @FXML
    private Button attacheFileTaskButton;
    @FXML
    private Button cancelNewTaskButton;
    @FXML
    private ImageView newTaskAttacheImageView;
    @FXML
    private DatePicker newTaskStartDate;
    @FXML
    private DatePicker newTaskEndDate;
    @FXML
    private TextArea newTaskDetailTextArea;
    @FXML
    private TextField newTaskNameField;
    @FXML
    private SplitMenuButton newTaskStartHour;
    @FXML
    private SplitMenuButton newTaskStartMin;
    @FXML
    private SplitMenuButton newTaskEndHour;
    @FXML
    private SplitMenuButton newTaskEndMin;
    private Task newTask;
    private CreateNewContactController newContactController = new CreateNewContactController();

    @FXML
    private void initialize() {
        handlePickDateTime(newTaskStartHour);
        handlePickDateTime(newTaskStartMin);
        handlePickDateTime(newTaskEndHour);
        handlePickDateTime(newTaskEndMin);
        addFileToTask();
    }

    private void handlePickDateTime(SplitMenuButton spmb) {
        for (final MenuItem item : spmb.getItems()) {
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    spmb.setText(item.getText());
                }
            });
        }
    }

    public void setTask(Task task) {
        if (task != null) {
            this.newTask = task;
            if (!TaskWindowController.isNewTask()) {
                newTaskStartHour.setText(ConvertData.getHour(newTask.getStartTime()));
                newTaskStartMin.setText(ConvertData.getMin(newTask.getStartTime()));
                newTaskEndHour.setText(ConvertData.getHour(newTask.getEndTime()));
                newTaskEndMin.setText(ConvertData.getMin(newTask.getEndTime()));
                newTaskStartDate.setValue(ConvertData.convertStringToLocalDate(newTask.getStartDate()));
                newTaskEndDate.setValue(ConvertData.convertStringToLocalDate(newTask.getEndDate()));
            } else {
                newTaskStartDate.setValue(LocalDate.now());
                newTaskEndDate.setValue(LocalDate.now());
            }
            newTaskNameField.setText(newTask.getName());
            newTaskDetailTextArea.setText(newTask.getDescription());
            newTaskAttacheImageView.setImage(ConvertData.convertToImage(newTask.getTaskImage()));
            completedTaskCheckBox.setSelected(newTask.isCompleted() == true ? true : false);
            CollectionTasks.updateTask(newTask);
        }
    }

    public Task getNewTask() {
        return newTask;
    }

    public void closeNewTaskWindow(ActionEvent actionEvent) {
        TaskWindowController.setAddTask(false);
        clearNewTaskFields();
        newContactController.hideWindow(cancelNewTaskButton);
    }

    public void clearNewTaskFields() {
        newTaskStartHour.setText("Часы");
        newTaskStartMin.setText("Минуты");
        newTaskEndHour.setText("Часы");
        newTaskEndMin.setText("Минуты");
        newTaskStartDate.setValue(null);
        newTaskEndDate.setValue(null);
        newTaskNameField.setText("");
        newTaskDetailTextArea.setText("");
        newTaskAttacheImageView.setImage(null);
        completedTaskCheckBox.setSelected(false);
    }

    public void addFileToTask() {
        newContactController.chooseImage(attacheFileTaskButton, newTaskAttacheImageView);
    }

    public void saveNewTask(ActionEvent actionEvent) {
        if (newTaskNameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Органайзер");
            alert.getDialogPane().setPrefWidth(500);
            alert.setHeaderText("Заполните имя задачи!");
            alert.setContentText("Имя задачи не может быть пустым!");
            alert.showAndWait();
        } else {
            this.newTask.setName(newTaskNameField.getText());
            this.newTask.setStartDate(newTaskStartDate.getValue() == null ? ConvertData.convertLocalDateToString(LocalDate.now()) : ConvertData.convertLocalDateToString(newTaskStartDate.getValue()));
            this.newTask.setEndDate(newTaskEndDate.getValue() == null ? "" : ConvertData.convertLocalDateToString(newTaskEndDate.getValue()));
            this.newTask.setStartTime((newTaskStartHour.getText().equals(hoursDefault) ? hoursDefaultValue : newTaskStartHour.getText()) + ":" + (newTaskStartMin.getText().equals(minutesDefault) ? minutesDefaultValue : newTaskStartMin.getText()));
            this.newTask.setEndTime((newTaskEndHour.getText().equals(hoursDefault) ? hoursDefaultValue : newTaskEndHour.getText()) + ":" + (newTaskEndMin.getText().equals(minutesDefault) ? minutesDefaultValue : newTaskEndMin.getText()));
            this.newTask.setDescription(newTaskDetailTextArea.getText().isEmpty() ? "" : newTaskDetailTextArea.getText());
            this.newTask.setCompleted(completedTaskCheckBox.isSelected() ? true : false);
            this.newTask.setTaskImage(newTaskAttacheImageView.getImage() == null ? new byte[0] : newContactController.getTempImage());
            TaskWindowController.setAddTask(true);
            CollectionTasks.updateTask(newTask);
            clearNewTaskFields();
            newContactController.hideWindow(cancelNewTaskButton);
        }
    }

    public void exitApplication(ActionEvent actionEvent) {
        LoginWindowController.exitApplication();
    }
}
