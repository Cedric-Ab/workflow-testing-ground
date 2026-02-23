package ch.zhaw.it.pm1.nullpointerexception.texteditor;

/**
 * Custom Exception for invalid usages of a command from the user.
 */
public class InvalidUsageException extends InvalidInputException {
    /**
     * Creates a new InvalidInputException.
     *
     * @param message      the error message
     * @param input        the invalid user input
     * @param correctUsage the correct usage / expected input
     */
    public InvalidUsageException(String message, String input, String correctUsage) {
        super(message, input, String.format("Correct usage: \"%s\".", correctUsage));
    }
}
