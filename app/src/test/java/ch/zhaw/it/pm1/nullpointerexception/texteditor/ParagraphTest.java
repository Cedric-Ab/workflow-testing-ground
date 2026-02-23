package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Paragraph class.
 */
public class ParagraphTest {

    @Nested
    class ReplaceTests {

        @Test
        void replace_singleMatch_replacedCorrectly() {
            Paragraph p = new Paragraph("hallo");
            p.replace("hallo", "tschau");
            assertEquals("tschau", p.getContent());
        }

        @Test
        void replace_multipleMatches_allReplaced() {
            Paragraph p = new Paragraph("hallo hallo");
            p.replace("hallo", "tschau");
            assertEquals("tschau tschau", p.getContent());
        }

        @Test
        void replace_deleteTarget_yieldsReducedString() {
            Paragraph p = new Paragraph("hallo hallo");
            p.replace("hallo", "");
            assertEquals(" ", p.getContent());
        }

        @Test
        void replace_noMatch_keepsOriginal() {
            Paragraph p = new Paragraph("hallo");
            p.replace("xyz", "abc");
            assertEquals("hallo", p.getContent());
        }

        @Test
        void replace_overlappingPatterns_resolvedLeftToRight() {
            Paragraph p = new Paragraph("aaaaaaaa");
            p.replace("aa", "b");
            assertEquals("bbbb", p.getContent());
        }
    }
}
