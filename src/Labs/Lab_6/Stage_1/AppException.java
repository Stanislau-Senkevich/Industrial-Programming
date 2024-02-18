package Labs.Lab_6.Stage_1;

public class AppException extends Exception {
    AppException(String err) {
        super("Exception: " + err);
    }
}
