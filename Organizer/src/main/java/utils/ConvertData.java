package utils;

import ezvcard.property.Photo;
import interfaces.impls.CollectionTasks;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import net.fortuna.ical4j.model.DateTime;
import objects.Task;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeUnit;

public class ConvertData {
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static LocalDate convertStringToLocalDate(String birthday) {
        if (birthday != null && birthday.length() > 5) {
            try {
                return DATE_FORMATTER.parse(birthday, LocalDate::from);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String convertLocalDateToString(LocalDate birthday) {
        if (birthday == null) {
            return null;
        }
        return DATE_FORMATTER.format(birthday);
    }

    public static Image convertToImage(Object source) {
        Image result = null;
        if (source instanceof byte[] && ((byte[]) source).length > 0) {
            InputStream byteArrayInputStream = new ByteArrayInputStream((byte[]) source);
            result = new Image(byteArrayInputStream, 450, 304, false, false);
        }
        return result;
    }

    public static byte[] convertImageToByteArray(Photo image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] result = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(image);
            out.flush();
            result = bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException NOP) {
            }
        }
        return result;
    }

    public static byte[] convertFileToByteArray(File source) {
        byte[] result = new byte[(int) source.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(source);
            fis.read(result, 0, (int) source.length());
        } catch (FileNotFoundException e) {
            Alert fnfe = new Alert(Alert.AlertType.ERROR);
            fnfe.setContentText("Невозможно открыть файл!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException NOP) {
                NOP.printStackTrace();
            }
        }
        return result;
    }

    public static String getMin(String time) {
        if (!time.isEmpty()) {
            return time.split(":")[1];
        }
        return null;
    }

    public static String getHour(String time) {
        if (!time.isEmpty()) {
            return time.split(":")[0];
        }
        return null;
    }

    public static String convertMillisecondsInHoursAndMinutes(DateTime seconds) {
        long time = seconds.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long min = TimeUnit.MILLISECONDS.toMinutes(time);
        String hour = Long.toString(hours).length() == 1 ? "0"+hours : Long.toString(hours);
        String minutes = Long.toString(min).length() == 1 ? min+"0" : Long.toString(min);
        return hour + ":" + minutes;
    }

    public static int countCompletedTasks() {
        int completedTasks = 0;
        for (Task item : CollectionTasks.getTaskList()) {
            if (item.isCompleted()) {
                completedTasks++;
            }
        }
        return completedTasks;
    }

    public static int countTodayTasks() {
        int tasks = 0;
        LocalDate today = LocalDate.now();
        int start;
        int end;
        for (Task item : CollectionTasks.getTaskList()) {
            start = today.compareTo(convertStringToLocalDate(item.getStartDate()));
            end = today.compareTo(convertStringToLocalDate(item.getEndDate()));
            if (!item.isCompleted() && start >= 0 && end <= 0) {
                tasks++;
            }
        }
        return tasks;
    }
}
