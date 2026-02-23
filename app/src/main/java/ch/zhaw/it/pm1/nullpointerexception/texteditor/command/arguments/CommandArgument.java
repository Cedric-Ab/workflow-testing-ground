package ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments;


import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;

public interface CommandArgument<T> {
    void parse(String arguments) throws InvalidInputException;

    T getParsed();
}
