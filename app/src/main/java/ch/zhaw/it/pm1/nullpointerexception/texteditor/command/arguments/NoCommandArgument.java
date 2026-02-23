package ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;

public class NoCommandArgument implements CommandArgument<Object> {
    @Override
    public void parse(String arguments) throws InvalidInputException {
        if (!arguments.trim().isBlank()) {
            throw new InvalidInputException("Invalid argument.", arguments, "No arguments are required.");
        }
    }

    @Override
    public Object getParsed() {
        throw new UnsupportedOperationException("No parsed value available for NoCommandArgument.");
    }
}
