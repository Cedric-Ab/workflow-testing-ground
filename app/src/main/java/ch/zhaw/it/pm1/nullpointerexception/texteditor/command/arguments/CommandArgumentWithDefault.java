package ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;

import java.util.Objects;

public class CommandArgumentWithDefault<T> implements CommandArgument<T> {
    private final CommandArgument<T> argument;
    private final T defaultValue;
    private T parsedValue;

    public CommandArgumentWithDefault(CommandArgument<T> argument, T defaultValue) {
        this.argument = Objects.requireNonNull(argument);
        this.defaultValue = defaultValue;
    }

    @Override
    public void parse(String arguments) throws InvalidInputException {
        if (arguments.trim().isBlank()) {
            parsedValue = defaultValue;
        } else {
            argument.parse(arguments);
            parsedValue = argument.getParsed();
        }
    }

    @Override
    public T getParsed() {
        return parsedValue;
    }
}
