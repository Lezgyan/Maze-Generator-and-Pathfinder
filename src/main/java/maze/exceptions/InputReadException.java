package maze.exceptions;

public class InputReadException extends RuntimeException {

    public InputReadException(String message, Exception exception) {
        super(message, exception);
    }

    public InputReadException(String message) {
        super(message);
    }

}
