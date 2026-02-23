package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.SubsequentAction;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.CommandArgument;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Superclass for all commands.
 * Also provides some methods as utility.
 */
public abstract class Command {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    protected final CommandArgument<?> argument;
    protected final Text text;
    protected final TextFormatter textFormatter;

    /**
     * Creates a new Command instance.
     *
     * @param arguments the additional input given upon calling of the command
     * @throws InvalidInputException if the additional input is invalid
     */
    public Command(String arguments, CommandArgument<?> commandArgument, Text text, TextFormatter textFormatter) throws InvalidInputException {
        this.text = Objects.requireNonNull(text, "text must not be null");
        this.textFormatter = Objects.requireNonNull(textFormatter, "textFormatter must not be null");
        this.argument = Objects.requireNonNull(commandArgument, "arguments must not be null");
        this.argument.parse(arguments);
    }

    /**
     * Returns the correct usage for the command.
     *
     * @return the correct usage for the command.
     */
    protected String getCorrectUsage() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    protected final String constructToString(LinkedHashMap<String, String> parameters) {
        parameters.putFirst("argument", argument.toString());

        String parametersAsString = parameters.entrySet().stream()
                .map(parameter -> {
                    if (parameter.getValue() != null) {
                        return "%s=\"%s\"".formatted(parameter.getKey(), parameter.getValue());
                    } else {
                        return "%s=null".formatted(parameter.getKey());
                    }

                })
                .collect(Collectors.joining(", "));
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " (" + parametersAsString + ")";
    }

    @Override
    public String toString() {
        return constructToString(new LinkedHashMap<>());
    }

    protected abstract List<FurtherInput<?>> getAllFurtherInputs();

    public Optional<FurtherInput<?>> getNextFurtherInput() {
        return getAllFurtherInputs().stream()
                .filter(Predicate.not(FurtherInput::isResolved))
                .findFirst();
    }

    /**
     * Executes the command.
     */
    public abstract void executeCommand();

    /**
     * Returns what subsequent action is to take after the execution of the command.
     *
     * @return the subsequent action
     */
    public abstract SubsequentAction getSubsequentAction();

    /**
     * Throws an exception stating that no output is required
     * To be used when a method handling output is called and no output is required.
     */
    protected final void handleIllegalGetOutputCall() {
        throw new IllegalStateException(
                String.format("No output is required for %s", this.getClass().getSimpleName()));
    }

    /**
     * Returns the output that is to be presented to the user.
     * Only available after the execution of the command.
     *
     * @return the output that is to be presented to the user
     */
    public abstract String getOutput();
}
