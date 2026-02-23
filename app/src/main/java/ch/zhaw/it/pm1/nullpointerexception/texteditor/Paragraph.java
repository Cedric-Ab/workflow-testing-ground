package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.Objects;

/**
 * Represents a single paragraph of text.
 */
public class Paragraph {

    private String content;

    /**
     * Creates a new paragraph with the given content.
     *
     * @param content initial content
     */
    public Paragraph(String content) {
        this.content = Objects.requireNonNull(content, "content must not be null");
    }

    public String getContent() {
        return content;
    }

    /**
     * Replaces all occurrences of the target string with the replacement string.
     *
     * @param target      the substring to replace, must not be empty and must not contain invalid characters
     * @param replacement the replacement text, must not contain invalid characters
     */
    public void replace(String target, String replacement) {
        Objects.requireNonNull(target, "target must not be null");
        Objects.requireNonNull(replacement, "replacement must not be null");

        content = content.replace(target, replacement);
    }
}
