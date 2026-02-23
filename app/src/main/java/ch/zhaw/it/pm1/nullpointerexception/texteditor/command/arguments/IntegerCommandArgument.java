package ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;

public class IntegerCommandArgument implements CommandArgument<Integer> {
    private final int minValue;
    private final int maxValue;
    protected int parsedValue;

    public IntegerCommandArgument(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public IntegerCommandArgument() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public Integer getParsed() {
        return parsedValue;
    }

    @Override
    public void parse(String arguments) throws InvalidInputException {
        try {
            parsedValue = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid argument.", arguments, "An integer is required.");
        }
        if (parsedValue < minValue) {
            throw new InvalidInputException("Invalid argument.", arguments,
                    "A integer greater than or equal to %d is required.".formatted(minValue));
        }
        if (parsedValue > maxValue) {
            throw new InvalidInputException("Invalid argument.", arguments,
                    "A integer less than or equal to %d is required.".formatted(maxValue));
        }
    }
}
