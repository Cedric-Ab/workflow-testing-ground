package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.List;
import java.util.logging.Logger;

/**
 * Provides utilities for handling of user input in context with invalid characters.
 */
public final class StringValidator {
    public static final String VALID_CHARACTERS =
            "abcdefghijklmnopqrstuvwxyz" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "äöüÄÖÜ" +
                    "0123456789" +
                    " " +
                    ".,:;-!?\"'()[]{}%@+*/\\&#$";
    private static final Logger logger = Logger.getLogger(StringValidator.class.getName());

    private StringValidator() {
        // Prevent instantiation
    }

    /**
     * Filters invalid characters from the string.
     *
     * @param string the String to filter
     * @return String containing only the valid characters
     */
    public static String filterInvalidCharacters(String string) {
        StringBuilder result = new StringBuilder(string.length());
        List<String> filteredChars = new java.util.ArrayList<>();

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (VALID_CHARACTERS.indexOf(c) >= 0) {
                result.append(c);
            } else {
                filteredChars.add(String.valueOf(c));
            }
        }

        logger.fine("Filtered invalid characters: %s".formatted(String.join(", ", filteredChars)));

        return result.toString();
    }

    /**
     * Checks if a String contains invalid characters.
     *
     * @param string the String that should get checked
     * @return if the String contains invalid characters
     */
    public static boolean containsInvalidCharacters(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (VALID_CHARACTERS.indexOf(string.charAt(i)) < 0) {
                logger.finer("Invalid character found: '%s'".formatted(string.charAt(i)));
                return true;
            }
        }
        return false;
    }
}
