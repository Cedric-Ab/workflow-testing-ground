package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the complete text as a list of paragraphs.
 */
public class Text {
    private final List<Paragraph> paragraphs = new ArrayList<>();

    /**
     * Returns the number of paragraphs.
     *
     * @return number of paragraphs
     */
    public int size() {
        return paragraphs.size();
    }

    private void validateIndex(int index, int offset) {
        if (index <= 0 || index > size() + offset)
            throw new IllegalArgumentException("Invalid paragraph number " + index);
    }

    /**
     * Returns the paragraph at the given index.
     *
     * @param index index of paragraph (1-based)
     * @return the requested paragraph or null if invalid
     */
    public Paragraph getParagraph(int index) throws IllegalArgumentException {
        validateIndex(index, 0);
        Paragraph paragraph = paragraphs.get(index - 1);
        Objects.requireNonNull(paragraph, "paragraph at index " + index + " must not be null");
        return paragraph;
    }

    /**
     * Inserts a paragraph at the given position.
     *
     * @param index   position where the paragraph should be inserted (1 = first)
     * @param content text of the new paragraph
     */
    public void addParagraph(int index, Paragraph content) throws IllegalArgumentException {
        Objects.requireNonNull(content, "content must not be null");
        validateIndex(index, 1);
        paragraphs.add(index - 1, content);
    }

    /**
     * Removes the paragraph at the given position.
     *
     * @param index position of the paragraph to remove (1 = first)
     */
    public void removeParagraph(int index) throws IllegalArgumentException {
        validateIndex(index, 0);
        paragraphs.remove(index - 1);
    }
}
