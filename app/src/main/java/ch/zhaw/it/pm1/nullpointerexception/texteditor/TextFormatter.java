package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.Objects;

/**
 * Responsible for the formatting of the Text.
 */
public class TextFormatter {
    private FormatStrategy formatStrategy = new FormatRawProcedure();

    /**
     * Main constructor.
     */
    public TextFormatter() {
    }

    /**
     * Set the format to raw.
     */
    public void setFormatRaw() {
        formatStrategy = new FormatRawProcedure();
    }

    /**
     * Set the format to fix.
     *
     * @param maxWidth desired max width
     */
    public void setFormatFix(int maxWidth) {
        formatStrategy = new FormatFixProcedure(maxWidth);
    }

    /**
     * Converts the Text to the currently set format.
     *
     * @param text the Text to be converted
     * @return the Text in the current format, may include {@code \n} (no trailing {@code \n})
     */
    public String convertToFormat(Text text) {
        Objects.requireNonNull(text, "text must not be null");

        return formatStrategy.format(text);
    }
}
