package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public record FormatFixProcedure(int maxWidth) implements FormatStrategy {
    public FormatFixProcedure {
        if (!isValidMaxWidth(maxWidth)) {
            throw new IllegalArgumentException("Invalid maximum width: " + maxWidth);
        }
    }

    /**
     * Checks whether the given width is valid.
     *
     * @param maxWidth the width to check
     * @return if the width is positive
     */
    public static boolean isValidMaxWidth(int maxWidth) {
        return maxWidth > 0;
    }

    @Override
    public String format(Text text) {
        Objects.requireNonNull(text, "text must not be null");

        List<String> convertedParagraphs = IntStream.range(1, text.size() + 1)
                .mapToObj(i -> formatParagraph(text.getParagraph(i)))
                .toList();

        return String.join("\n", convertedParagraphs);
    }

    /**
     * Returns the paragraph content formatted to the given maximum line width.
     *
     * @param paragraph the paragraph to format
     * @return the formatted paragraph text
     */
    public String formatParagraph(Paragraph paragraph) {
        List<String> lines = new ArrayList<>();
        String remainingText = paragraph.getContent();

        while (!remainingText.isEmpty()) {
            int breakPosition = findBreakPosition(remainingText, maxWidth);
            String line = remainingText.substring(0, breakPosition);
            lines.add(line);
            remainingText = remainingText.substring(breakPosition);
        }

        return String.join("\n", lines);
    }

    private static boolean doesLineFit(String line, int maxWidth) {
        return (line.stripTrailing().length() <= maxWidth);
    }

    private static int findBreakPosition(String text, int maxWidth) {
        if (doesLineFit(text, maxWidth)) {
            return text.length();
        }

        int lastPossibleBreak = -1;

        boolean breakLoop = false;
        for (int i = 0; i < text.length() && !breakLoop; i++) {
            char currentChar = text.charAt(i);
            if (Character.isWhitespace(currentChar)) {
                lastPossibleBreak = i + 1;
            } else {
                if (i >= maxWidth) {
                    // break the line after the last space, even if the spaces exceed maxWidth
                    breakLoop = true;
                }
            }
        }

        if (lastPossibleBreak == -1) {
            lastPossibleBreak = maxWidth;
        }

        return lastPossibleBreak;
    }
}
