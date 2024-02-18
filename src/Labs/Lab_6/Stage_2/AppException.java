package Labs.Lab_6.Stage_2;

public class AppException extends Exception {
    AppException(String err) {
        super("Exception: " + err);
    }
}
