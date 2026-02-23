package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Tests for CommandParser
 */
class CommandParserTest {
    private final CommandParser commandParser = new CommandParser(new Text(), new TextFormatter());

    @Nested
    class EdgeCaseParsingTests {

        @Test
        void differentCase_returnsExit() throws InvalidInputException {
            assertInstanceOf(Exit.class, commandParser.getCommand("Exit"));
            assertInstanceOf(Exit.class, commandParser.getCommand("exit"));
            assertInstanceOf(Exit.class, commandParser.getCommand("EXIT"));
            assertInstanceOf(Exit.class, commandParser.getCommand("eXiT"));
        }

        @Test
        void trailingSpace_returnsExit() throws InvalidInputException {
            assertInstanceOf(Exit.class, commandParser.getCommand("exit "));
        }

        @Test
        void leadingSpace_returnsExit() throws InvalidInputException {
            assertInstanceOf(Exit.class, commandParser.getCommand(" exit"));
        }

    }

    @Nested
    class ParameterParsingTests {

        @Test
        void addCommand_returnsAdd() throws InvalidInputException {
            assertInstanceOf(Add.class, commandParser.getCommand("add"));
        }

        @Test
        void addWithParameter_returnsAdd() throws InvalidInputException {
            assertInstanceOf(Add.class, commandParser.getCommand("add 1"));
        }

        @Test
        void addWithParameterAndManySpaces_returnsAdd() throws InvalidInputException {
            assertInstanceOf(Add.class, commandParser.getCommand("add     1"));
        }
    }

    @Nested
    class SimpleCommandTests {

        @Test
        void addCommand_returnsAdd() throws InvalidInputException {
            assertInstanceOf(Add.class, commandParser.getCommand("add"));
        }

        @Test
        void dummyCommand_returnsDummy() throws InvalidInputException {
            assertInstanceOf(Dummy.class, commandParser.getCommand("dummy"));
        }

        @Test
        void replaceCommand_returnsReplace() throws InvalidInputException {
            assertInstanceOf(Replace.class, commandParser.getCommand("replace"));
        }

        @Test
        void printCommand_returnsPrint() throws InvalidInputException {
            assertInstanceOf(Print.class, commandParser.getCommand("print"));
        }

        @Test
        void indexCommand_returnsIndex() throws InvalidInputException {
            assertInstanceOf(Index.class, commandParser.getCommand("Index"));
        }

        @Test
        void deleteCommand_returnsDelete() throws InvalidInputException {
            assertInstanceOf(Delete.class, commandParser.getCommand("del"));
            assertInstanceOf(Delete.class, commandParser.getCommand("delete"));
        }
    }

    @Nested
    class FormatRawCommandTests {

        @Test
        void formatRawCommand_returnsFormatRaw() throws InvalidInputException {
            assertInstanceOf(FormatRaw.class, commandParser.getCommand("format raw"));
        }

        @Test
        void formatRawCommandWithManySpaces_returnsFormatRaw() throws InvalidInputException {
            assertInstanceOf(FormatRaw.class, commandParser.getCommand("format     raw"));
        }

        @Test
        void formatRawCommandWithoutSpace_returnsFormatRaw() throws InvalidInputException {
            assertInstanceOf(FormatRaw.class, commandParser.getCommand("formatraw"));
        }
    }

    @Nested
    class FormatFixCommandTests {

        @Test
        void formatFixCommand_returnsFormatFix() throws InvalidInputException {
            assertInstanceOf(FormatFix.class, commandParser.getCommand("format fix 5"));
        }

        @Test
        void formatFixCommandWithManySpaces_returnsFormatFix() throws InvalidInputException {
            assertInstanceOf(FormatFix.class, commandParser.getCommand("formatfix     5"));
        }
    }
}
