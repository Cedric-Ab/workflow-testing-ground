package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public record FormatRawProcedure() implements FormatStrategy {
    @Override
    public String format(Text text) {
        Objects.requireNonNull(text, "text must not be null");

        List<String> convertedParagraphs = IntStream.range(1, text.size() + 1)
                .mapToObj(i -> "<" + i + ">: " + text.getParagraph(i).getContent())
                .toList();

        return String.join("\n", convertedParagraphs);
    }
}
