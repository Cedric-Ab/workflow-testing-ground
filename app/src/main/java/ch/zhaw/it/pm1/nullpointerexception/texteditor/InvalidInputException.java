package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.Objects;

/**
 * Custom Exception for invalid input from the user.
 */
public class InvalidInputException extends Exception {
    private final String input;
    private final String userInformation;

    /**
     * Creates a new InvalidInputException.
     *
     * @param message         the error message
     * @param input           the invalid user input
     * @param userInformation the user information message
     */
    public InvalidInputException(String message, String input, String userInformation) {
        super(Objects.requireNonNull(message, "message must not be null"));

        this.input = Objects.requireNonNull(input, "input must not be null");
        this.userInformation = Objects.requireNonNull(userInformation, "userInformation must not be null");
    }

    @Override
    public String toString() {
        return "%s: input=\"%s\", userInformation=\"%s\"".formatted(
                super.toString(), input, userInformation);
    }

    /**
     * Returns the input that lead to the exception.
     *
     * @return the input that lead to the exception
     */
    public String getInput() {
        return input;
    }

    /**
     * Returns the message explaining what was expected.
     *
     * @return the user information message
     */
    public String getUserInformation() {
        return userInformation;
    }
}
