package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Provides methods to interact with the user via console input and output.
 */
public class UserInterface {
    private static final Logger logger = Logger.getLogger(UserInterface.class.getName());

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Creates a new UserInterface instance.
     */
    public UserInterface() {
    }

    /**
     * Reads a console input line.
     *
     * @return input line
     */
    public String getUserInput() {
        String nextLine = scanner.nextLine();
        logger.fine("User Input: \"%s\"".formatted(nextLine));
        return nextLine;
    }

    /**
     * Prints the passed string into the console, so it's visible for the user.
     *
     * @param output string to show in the console
     */
    public void printOutput(String output) {
        Objects.requireNonNull(output, "Output must not be null");

        logger.fine("Outputting to System.out: \"%s\"".formatted(output));

        System.out.print(output);
    }

    /**
     * Prints an error message to the error output stream.
     *
     * @param output error message to show
     */
    public void printError(String output) {
        Objects.requireNonNull(output, "Output must not be null");

        logger.fine("Outputting to System.err: \"%s\"".formatted(output));

        System.err.print(output);
    }
}
