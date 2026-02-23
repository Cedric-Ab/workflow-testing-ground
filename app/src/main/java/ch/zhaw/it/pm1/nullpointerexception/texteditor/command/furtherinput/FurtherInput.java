package ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidUsageException;

public interface FurtherInput<T> {
    boolean isResolved();

    String getPrompt();

    void resolve(String input) throws InvalidUsageException;

    T getValue();
}
