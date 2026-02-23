package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatFixProcedureTest {

    @Test
    void format() {
        FormatFixProcedure f = new FormatFixProcedure(6);
        Text text = new Text();

        assertEquals("", f.format(text));

        text.addParagraph(1, new Paragraph("hallo "));
        assertEquals("hallo ", f.format(text));
        text.addParagraph(2, new Paragraph("hallo hallo"));
        assertEquals("hallo \nhallo \nhallo", f.format(text));
    }

    @Nested
    class IsValidMaxWidth {
        @Test
        void isValidMaxLength_positiveNumber_returnsTrue() {
            assertTrue(FormatFixProcedure.isValidMaxWidth(1));
            assertTrue(FormatFixProcedure.isValidMaxWidth(10));
            assertTrue(FormatFixProcedure.isValidMaxWidth(500));
        }

        @Test
        void isValidMaxLength_zero_returnsFalse() {
            assertFalse(FormatFixProcedure.isValidMaxWidth(0));
        }

        @Test
        void isValidMaxLength_negativeNumber_returnsFalse() {
            assertFalse(FormatFixProcedure.isValidMaxWidth(-1));
            assertFalse(FormatFixProcedure.isValidMaxWidth(-20));
        }
    }

    @Nested
    class FormatParagraph {
        FormatFixProcedure f2 = new FormatFixProcedure(2);
        FormatFixProcedure f10 = new FormatFixProcedure(10);

        @Test
        void longWordsAreSplitCorrectly() {
            Paragraph p = new Paragraph("hallo");
            assertEquals("ha\nll\no", f2.formatParagraph(p));
        }

        @Test
        void noBreakRequired_whenLineIsLongEnough() {
            Paragraph p = new Paragraph("hallo");
            assertEquals("hallo", f10.formatParagraph(p));
        }

        @Test
        void emptyString_returnsEmptyString() {
            Paragraph p = new Paragraph("");
            assertEquals("", f10.formatParagraph(p));
        }

        @Test
        void spaceHandling_preservedBeforeBreak() {
            Paragraph p = new Paragraph("hallo  hallo");
            assertEquals("hallo  \nhallo", f10.formatParagraph(p));
        }

        @Test
        void breakOccursAfterSpace() {
            Paragraph p1 = new Paragraph("hallo hallo");
            for (int i = 5; i <= 7; i++) {
                assertEquals("hallo \nhallo", new FormatFixProcedure(i).formatParagraph(p1));
            }

            Paragraph p2 = new Paragraph("hallo    hallo");
            for (int i = 5; i <= 10; i++) {
                assertEquals("hallo    \nhallo", new FormatFixProcedure(i).formatParagraph(p2));
            }
        }

        @Test
        void specialCharacters_splitCorrectly() {
            Paragraph p = new Paragraph(".,:;-!?'()\"%@+*[]{}/\\&#$dd");
            String expected = ".,:;-!?'()\"%@+*[]{}/\\&#$d\nd";
            assertEquals(expected, new FormatFixProcedure(25).formatParagraph(p));
        }
    }
}
