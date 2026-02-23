package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {
    @Nested
    class containsInvalidCharacters {
        @Test
        void invalidCharacterDetection() {
            assertTrue(StringValidator.containsInvalidCharacters("€"));
            assertTrue(StringValidator.containsInvalidCharacters("Hallo€"));
            assertTrue(StringValidator.containsInvalidCharacters("Straße"));
            assertTrue(StringValidator.containsInvalidCharacters("Test~"));
        }

        @Test
        void validCharacterDetection() {
            assertFalse(StringValidator.containsInvalidCharacters("Hallo"));
            assertFalse(StringValidator.containsInvalidCharacters("Hallo Welt"));
            assertFalse(StringValidator.containsInvalidCharacters("Wässup!"));
        }
    }

    @Nested
    class filterInvalidCharacters {
        @Test
        void invalidCharacters() {
            assertEquals("Ll", StringValidator.filterInvalidCharacters("Lél"));
            assertEquals("HMM?", StringValidator.filterInvalidCharacters("¨HMM?"));
            assertEquals("helloworld", StringValidator.filterInvalidCharacters("hello_world"));

        }

        @Test
        void validCharacters() {
            assertEquals("Hallo", StringValidator.filterInvalidCharacters("Hallo"));
            assertEquals("Wässup!", StringValidator.filterInvalidCharacters("Wässup!"));
            assertEquals("[$(%&()", StringValidator.filterInvalidCharacters("[$(%&()"));
            assertEquals("123ABC", StringValidator.filterInvalidCharacters("123ABC"));
            assertEquals("ÄÖÜäöü", StringValidator.filterInvalidCharacters("ÄÖÜäöü"));
            assertEquals("Space OK", StringValidator.filterInvalidCharacters("Space OK"));
            assertEquals("Gar%$#!", StringValidator.filterInvalidCharacters("Gar%$#!"));
            assertEquals("{}[]\\\\", StringValidator.filterInvalidCharacters("{}[]\\\\"));
            assertEquals("O'Connor", StringValidator.filterInvalidCharacters("O'Connor"));
        }
    }
}
