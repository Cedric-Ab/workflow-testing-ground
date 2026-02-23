package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;

/**
 * Factory for the Commands.
 */
@FunctionalInterface
public interface CommandFactory {
    Command create(String additionalInput, Text text, TextFormatter textFormatter) throws InvalidInputException;
}
