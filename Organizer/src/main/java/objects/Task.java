package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
    private SimpleIntegerProperty taskID;
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty description = new SimpleStringProperty("");
    private SimpleStringProperty startDate = new SimpleStringProperty("");
    private SimpleStringProperty endDate = new SimpleStringProperty("");
    private SimpleStringProperty startTime = new SimpleStringProperty("");
    private SimpleStringProperty endTime = new SimpleStringProperty("");
    private SimpleBooleanProperty isImportant = new SimpleBooleanProperty(false);
    private SimpleObjectProperty personImage = new SimpleObjectProperty(new byte[0]);

    public Task() {
    }

    public Task(SimpleStringProperty name, SimpleStringProperty description, SimpleStringProperty startDate, SimpleStringProperty endDate,
                SimpleStringProperty startTime, SimpleStringProperty endTime, SimpleBooleanProperty isImportant, SimpleObjectProperty personImage) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isImportant = isImportant;
        this.personImage = personImage;
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

    public boolean isIsImportant() {
        return isImportant.get();
    }

    public SimpleBooleanProperty isImportantProperty() {
        return isImportant;
    }

    public void setIsImportant(boolean isImportant) {
        this.isImportant.set(isImportant);
    }

    public Object getPersonImage() {
        return personImage.get();
    }

    public SimpleObjectProperty personImageProperty() {
        return personImage;
    }

    public void setPersonImage(Object personImage) {
        this.personImage.set(personImage);
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
        if (isImportant != null ? !isImportant.equals(task.isImportant) : task.isImportant != null) return false;
        return personImage != null ? personImage.equals(task.personImage) : task.personImage == null;

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
        result = 31 * result + (isImportant != null ? isImportant.hashCode() : 0);
        result = 31 * result + (personImage != null ? personImage.hashCode() : 0);
        return result;
    }
}
