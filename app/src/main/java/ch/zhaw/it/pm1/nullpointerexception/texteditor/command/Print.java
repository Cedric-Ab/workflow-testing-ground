package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.SubsequentAction;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.NoCommandArgument;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents the Print Command.
 * Triggers output of the current text in the active format.
 */
public class Print extends Command {
    private String formattedOutput;

    /**
     * Creates a new Print Command instance.
     *
     * @param arguments the additional input given upon calling of the command
     * @throws InvalidInputException if the additional input is invalid
     */
    public Print(String arguments, Text text, TextFormatter textFormatter) throws InvalidInputException {
        super(arguments, new NoCommandArgument(), text, textFormatter);
    }

    @Override
    public String toString() {
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("output", formattedOutput);
        return constructToString(parameters);
    }

    @Override
    protected List<FurtherInput<?>> getAllFurtherInputs() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCommand() {
        formattedOutput = textFormatter.convertToFormat(text);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link SubsequentAction#OUTPUT}
     */
    @Override
    public SubsequentAction getSubsequentAction() {
        return SubsequentAction.OUTPUT;
    }

    /**
     * {@inheritDoc}
     *
     * @return the Text in the current format
     */
    @Override
    public String getOutput() {
        return formattedOutput;
    }
}
