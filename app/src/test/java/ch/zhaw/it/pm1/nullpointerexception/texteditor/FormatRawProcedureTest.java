package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatRawProcedureTest {
    private FormatRawProcedure f;

    @BeforeEach
    void setUp() {
        f = new FormatRawProcedure();
    }

    @Test
    void emptyText() {
        assertEquals("", f.format(new Text()));
    }

    @Test
    void nonEmptyText() {
        Text t = new Text();

        t.addParagraph(1, new Paragraph("tests test"));
        assertEquals("<1>: tests test", f.format(t));

        t.addParagraph(2, new Paragraph("test2"));
        assertEquals("<1>: tests test\n<2>: test2", f.format(t));
    }
}
