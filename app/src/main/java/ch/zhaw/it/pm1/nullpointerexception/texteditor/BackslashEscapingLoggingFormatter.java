package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class BackslashEscapingLoggingFormatter extends Formatter {

    private final SimpleFormatter delegate = new SimpleFormatter();

    @Override
    public String format(LogRecord record) {
        // Get the normal formatted message
        String formatted = delegate.format(record);

        // Extract the actual message portion
        String msg = formatMessage(record);

        // Escape backslashes and newline characters
        String escapedMsg = msg
                .replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

        // Replace the original message in the formatted string
        return formatted.replace(msg, escapedMsg);
    }
}
