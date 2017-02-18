package utils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public static byte[] convertImageToByteArray(Image image) {
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
}
