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

    public static LocalDate convertStringToLocalDate (String birthday) {
        if (birthday != null && !birthday.isEmpty() && birthday != " ") {
            try {
                return DATE_FORMATTER.parse(birthday, LocalDate::from);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String convertLocalDateToString(LocalDate birthday) {
        if(birthday == null) {
            return null;
        }
        return DATE_FORMATTER.format(birthday);
    }

    public static Image convertToImage (Object source) {
        Image result = null;
        if (source instanceof byte[]) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])source);
            result = new Image(byteArrayInputStream, 450, 300, false, false);
        }
        return result;
    }

    public static byte[] convertToByteArray (File source) {
        byte[] result = new byte[(int) source.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(source);
            if(fis.read(result) == -1){

            }
        }
        catch (FileNotFoundException e) {
            Alert fnfe = new Alert(Alert.AlertType.ERROR);
            fnfe.setContentText("Невозможно открыть файл!");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fis.close();
            } catch (IOException NOP) {

            }
        }
        return result;
    }
}
