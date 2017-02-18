package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
    private SimpleIntegerProperty taskID = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty description = new SimpleStringProperty("");
    private SimpleStringProperty startDate = new SimpleStringProperty("");
    private SimpleStringProperty endDate = new SimpleStringProperty("");
    private SimpleStringProperty startTime = new SimpleStringProperty("");
    private SimpleStringProperty endTime = new SimpleStringProperty("");
    private SimpleObjectProperty<byte[]> taskImage = new SimpleObjectProperty<>();
    private SimpleBooleanProperty completed = new SimpleBooleanProperty(false);

    public Task() {
    }

    public Task(Integer id, String name, String description, String startDate, String endDate,
                String startTime, String endTime, byte[] taskImage, boolean completed) {
        this.taskID = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.startTime = new SimpleStringProperty(startTime);
        this.endTime = new SimpleStringProperty(endTime);
        this.taskImage = new SimpleObjectProperty(taskImage);
        this.completed = new SimpleBooleanProperty(completed);
    }

    public int getTaskID() {
        return taskID.get();
    }

    public SimpleIntegerProperty taskIDProperty() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID.set(taskID);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public SimpleStringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getEndDate() {
        return endDate.get();
    }

    public SimpleStringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }

    public String getStartTime() {
        return startTime.get();
    }

    public SimpleStringProperty startTimeProperty() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public String getEndTime() {
        return endTime.get();
    }

    public SimpleStringProperty endTimeProperty() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public byte[] getTaskImage() {
        return taskImage.get();
    }

    public SimpleObjectProperty<byte[]> taskImageProperty() {
        return taskImage;
    }

    public void setTaskImage(byte[] taskImage) {
        this.taskImage.set(taskImage);
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public SimpleBooleanProperty completedProperty() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (taskID != null ? !taskID.equals(task.taskID) : task.taskID != null) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        if (startDate != null ? !startDate.equals(task.startDate) : task.startDate != null) return false;
        if (endDate != null ? !endDate.equals(task.endDate) : task.endDate != null) return false;
        if (startTime != null ? !startTime.equals(task.startTime) : task.startTime != null) return false;
        if (endTime != null ? !endTime.equals(task.endTime) : task.endTime != null) return false;
        if (taskImage != null ? !taskImage.equals(task.taskImage) : task.taskImage != null) return false;
        return completed != null ? completed.equals(task.completed) : task.completed == null;
    }

    @Override
    public int hashCode() {
        int result = taskID != null ? taskID.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (taskImage != null ? taskImage.hashCode() : 0);
        result = 31 * result + (completed != null ? completed.hashCode() : 0);
        return result;
    }
}
