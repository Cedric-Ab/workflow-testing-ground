package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IndexerTest {

    @Nested
    class GetIndexDirectory {
        private static final Map<String, Set<Integer>> EMPTY_MAP = Map.of();

        private static Map<String, Set<Integer>> getIndexDirectoryOf(String... paragraphContents) {
            Text text = new Text();

            for (String paragraphContent : paragraphContents) {
                text.addParagraph(text.size() + 1, new Paragraph(paragraphContent));
            }

            return Indexer.getIndexDirectory(text);
        }

        @Test
        void returnsEmptyInvalidWords() {
            assertEquals(EMPTY_MAP, getIndexDirectoryOf());
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("", " "));
        }

        @Test
        void returnsEmptyValidWords() {
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("Hallo", "Moin Hallo"));
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("Hallo", "Hallo", "Moin", "Moin"));
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("Hallo Hallo HAllo"));
        }

        @Test
        void returnsEmptyValidAndInvalidWords() {
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("hallo Hallo hallo", "Hallo"));
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("Hallo hallo Hallo"));

            // Special Characters are part of the word
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("Hallo Hallo,Hallo HalloHallo HalloHallo"));
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("Hallo Hallo.Hallo HalloHallo HalloHallo"));
            assertEquals(EMPTY_MAP, getIndexDirectoryOf("Hallo Hallo_Hallo HalloHallo HalloHallo"));
        }

        @Test
        void returnsNotEmptyOneDistinctWord() {
            // exactly 4 words
            assertEquals(Map.of("Hallo", Set.of(1)),
                    getIndexDirectoryOf("Hallo Hallo Hallo Hallo", ""));
            assertEquals(Map.of("Hallo", Set.of(1, 2)),
                    getIndexDirectoryOf("Hallo Hallo", "Hallo Hallo"));
            assertEquals(Map.of("Hallo", Set.of(1, 2, 3, 4)),
                    getIndexDirectoryOf("Hallo", "Hallo", "Hallo", "Hallo"));
            // exactly 4 words but special characters at the end
            assertEquals(Map.of("Hallo", Set.of(1, 2)),
                    getIndexDirectoryOf("Hallo", "Hallo, Hallo, Hallo"));

            // more than 4 words
            assertEquals(Map.of("Hallo", Set.of(1)),
                    getIndexDirectoryOf("Hallo Hallo Hallo Hallo"));
            assertEquals(Map.of("Hallo", Set.of(1, 2)),
                    getIndexDirectoryOf("Hallo Hallo Hallo", "Hallo Hallo Hallo"));

            // words with special characters inside
            assertEquals(Map.of("Ha!!llo", Set.of(1)), getIndexDirectoryOf("Ha!!llo Ha!!llo Ha!!llo Ha!!llo"));

            // words with different case are not the same word
            assertEquals(Map.of("HALLO", Set.of(1)), getIndexDirectoryOf("HALLO HALLO HALLO HALLO", "Hallo"));
        }

        @Test
        void returnsNotEmptyMultipleDistinctWords() {
            assertEquals(Map.of("Hallo", Set.of(1)),
                    getIndexDirectoryOf("Hallo Hallo Hallo Hallo", "Moin"));
            assertEquals(Map.of("Hallo", Set.of(1, 2)),
                    getIndexDirectoryOf("Hallo Hallo", "Moin Hallo Hallo"));
            assertEquals(Map.of("Hallo", Set.of(1, 2, 3)),
                    getIndexDirectoryOf("Hallo", "Moin Hallo", "Hallo Hallo"));
            assertEquals(Map.of("Hallo", Set.of(1, 2, 3, 4, 5)),
                    getIndexDirectoryOf("Hallo", "Moin Hallo", "Hallo", "Hallo Welt", "Hallo Mensch"));

            assertEquals(Map.of("Tigurini", Set.of(1, 2)),
                    getIndexDirectoryOf("Tigurini Tigurini Helvetii", "Tigurini Tigurini"));
            assertEquals(Map.of("Helvetii", Set.of(1, 2), "Tigurini", Set.of(1)),
                    getIndexDirectoryOf("Tigurini Tigurini Tigurini Tigurini Helvetii Helvetii", "Helvetii Helvetii"));
            assertEquals(Map.of("Gallia", Set.of(3), "Tigurini", Set.of(1)),
                    getIndexDirectoryOf("Tigurini Tigurini Tigurini Tigurini", "Helvetii Helvetii", "Gallia Gallia Gallia Gallia Gallia"));
        }

    }

    @Nested
    class ExtractWordsFromParagraphTest {

        @Test
        void singleCapitalizedWord() {
            Paragraph p = new Paragraph("Hallohallohallo");

            Map<String, Integer> expected = new HashMap<>();
            expected.put("Hallohallohallo", 1);

            assertEquals(expected, Indexer.extractWordsFromParagraph(p));
        }

        @Test
        void repeatedCapitalizedWords() {
            Paragraph p = new Paragraph("Hallo, Hallo, Hallo");

            Map<String, Integer> expected = new HashMap<>();
            expected.put("Hallo", 3);

            assertEquals(expected, Indexer.extractWordsFromParagraph(p));
        }

        @Test
        void repeatedMixedWords() {
            Paragraph p = new Paragraph("Hallo, Guten, Hallo");

            Map<String, Integer> expected = new HashMap<>();
            expected.put("Hallo", 2);
            expected.put("Guten", 1);

            assertEquals(expected, Indexer.extractWordsFromParagraph(p));
        }

        @Test
        void lowercaseIgnored() {
            Paragraph p = new Paragraph("Hallo, hallo, HALLO");

            Map<String, Integer> result = Indexer.extractWordsFromParagraph(p);

            assertFalse(result.containsKey("hallo"));

            assertEquals(1, result.get("Hallo"));
            assertEquals(1, result.get("HALLO"));
            assertEquals(2, result.size());
        }

        @Test
        void invertedCaseAndSingleLetter() {
            Paragraph p = new Paragraph("bONJOUR A Tous");

            Map<String, Integer> expected = new HashMap<>();
            expected.put("Tous", 1);

            assertEquals(expected, Indexer.extractWordsFromParagraph(p));
        }

        @Test
        void startsWithUmlaut() {
            Paragraph p = new Paragraph("Äquivalenzklasse");

            Map<String, Integer> expected = new HashMap<>();
            expected.put("Äquivalenzklasse", 1);

            assertEquals(expected, Indexer.extractWordsFromParagraph(p));
        }

        @Test
        void differentWordsSplitWithSpace() {
            Paragraph p = new Paragraph("Ja Nein");

            Map<String, Integer> expected = new HashMap<>();
            expected.put("Ja", 1);
            expected.put("Nein", 1);

            assertEquals(expected, Indexer.extractWordsFromParagraph(p));
        }

        @Test
        void mixedWords_onlyLeadingUppercaseCounted() {
            Paragraph p = new Paragraph("Tschau, Bis Morgen");

            Map<String, Integer> expected = new HashMap<>();
            expected.put("Tschau", 1);
            expected.put("Bis", 1);
            expected.put("Morgen", 1);

            assertEquals(expected, Indexer.extractWordsFromParagraph(p));
        }

        @Test
        void wordStartingWithDigit_ignored() {
            Paragraph p = new Paragraph("3D Test Hallo");

            Map<String, Integer> result = Indexer.extractWordsFromParagraph(p);

            assertFalse(result.containsKey("3D"));

            assertEquals(1, result.get("Test"));
            assertEquals(1, result.get("Hallo"));
            assertEquals(2, result.size());
        }

        @Test
        void wordStartingWithSpecialChar_ignored() {
            Paragraph p = new Paragraph("#Zhaw Hallo");

            Map<String, Integer> result = Indexer.extractWordsFromParagraph(p);

            assertEquals(1, result.size());
            assertEquals(1, result.get("Hallo"));
        }

        @Test
        void entireLowercase_returnsEmptyMap() {
            Paragraph p = new Paragraph("hallo welt test");

            assertTrue(Indexer.extractWordsFromParagraph(p).isEmpty());
        }

        @Test
        void emptyString_returnsEmptyMap() {
            Paragraph p = new Paragraph("");

            assertTrue(Indexer.extractWordsFromParagraph(p).isEmpty());
        }

        @Test
        void emptyStringWithCommas_returnsEmptyMap() {
            Paragraph p = new Paragraph(", , , , ");

            assertTrue(Indexer.extractWordsFromParagraph(p).isEmpty());
        }
    }

    @Nested
    class ExtractWords {
        @Test
        void onlyValidWords() {
            String paragraph;
            List<String> words;
            List<String> expected;

            paragraph = "Hello World ThisIsAValidWord123 AnotherValidWord";
            words = Indexer.extractWords(paragraph).stream().sorted().toList();
            expected = Stream.of("Hello", "World", "ThisIsAValidWord123", "AnotherValidWord").sorted().toList();
            assertEquals(expected, words);

            paragraph = "Hello World ThisIsAValidWord123 AnotherValidWord Hello Hello World";
            words = Indexer.extractWords(paragraph).stream().sorted().toList();
            expected = Stream.of("Hello", "Hello", "Hello", "World", "World", "ThisIsAValidWord123", "AnotherValidWord").sorted().toList();
            assertEquals(expected, words);
        }

        @Test
        void mixedValidAndInvalidWords() {
            String paragraph;
            List<String> words;
            List<String> expected;

            paragraph = "Hello world! ThisIsAValidWord123 X 12AnotherInvalidWord1 AnotherValidWord!!!";
            words = Indexer.extractWords(paragraph).stream().sorted().toList();
            expected = Stream.of("Hello", "ThisIsAValidWord123", "AnotherValidWord").sorted().toList();
            assertEquals(expected, words);

            paragraph = "ValidWord1 Valid-word2 ValidWord3!!! 123InvalidWord y Another$Valid$Word.";
            words = Indexer.extractWords(paragraph).stream().sorted().toList();
            expected = Stream.of("ValidWord1", "Valid-word2", "ValidWord3", "Another$Valid$Word").sorted().toList();
            assertEquals(expected, words);
        }

        @Test
        void onlyInvalidWords() {
            String paragraph;
            List<String> words;

            paragraph = "invalidWord1 2ndInvalidWord !@#$% ^&*()";
            words = Indexer.extractWords(paragraph);
            assertTrue(words.isEmpty());

            paragraph = "x y z 1 2 3 !!! ??? ...";
            words = Indexer.extractWords(paragraph);
            assertTrue(words.isEmpty());
        }

        @Test
        void emptyParagraph() {
            String paragraph = "";
            List<String> words = Indexer.extractWords(paragraph);
            assertTrue(words.isEmpty());
        }
    }

    @Nested
    class TrimWord {
        @Test
        void nothingToTrim() {
            assertEquals("Hello", Indexer.trimWord("Hello"));
            assertEquals("Hello123", Indexer.trimWord("Hello123"));
            assertEquals("HELLO", Indexer.trimWord("HELLO"));
            assertEquals("Hello.Hello", Indexer.trimWord("Hello.Hello"));
        }

        @Test
        void trimEndingInvalidCharacters() {
            assertEquals("Hello", Indexer.trimWord("Hello!!!"));
            assertEquals("Word", Indexer.trimWord("Word..."));
            assertEquals("Word", Indexer.trimWord("Word,"));
            assertEquals("Test123", Indexer.trimWord("Test123???"));
            assertEquals("Hello.Hello", Indexer.trimWord("Hello.Hello."));
        }
    }

    @Nested
    class IsValidWord {
        @Test
        void validWords() {
            assertTrue(Indexer.isValidWord("Hello"));
            assertTrue(Indexer.isValidWord("HELLO"));
            assertTrue(Indexer.isValidWord("World123"));
            assertTrue(Indexer.isValidWord("A1B2C3"));
            assertTrue(Indexer.isValidWord("A1-B2C3"));
            assertTrue(Indexer.isValidWord("A.......2"));
            assertTrue(Indexer.isValidWord("W[$(%&()]b"));
            assertTrue(Indexer.isValidWord("Valid-word2"));
            assertTrue(Indexer.isValidWord("Another$Valid$Word"));
        }

        @Test
        void invalidWordsToShort() {
            assertFalse(Indexer.isValidWord(""));
            assertFalse(Indexer.isValidWord(" "));
            assertFalse(Indexer.isValidWord("X"));
        }

        @Test
        void invalidWordsStartingCharacter() {
            assertFalse(Indexer.isValidWord("hello"));
            assertFalse(Indexer.isValidWord("1Hello"));
            assertFalse(Indexer.isValidWord("-World"));
            assertFalse(Indexer.isValidWord(".Test"));
            assertFalse(Indexer.isValidWord("!Invalid"));
        }

        @Test
        void invalidWordsEndingCharacter() {
            assertFalse(Indexer.isValidWord("Hello!"));
            assertFalse(Indexer.isValidWord("World?"));
            assertFalse(Indexer.isValidWord("Test-"));
            assertFalse(Indexer.isValidWord("Example_"));
            assertFalse(Indexer.isValidWord("S!!!!"));
        }
    }
}
