package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.SubsequentAction;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.NoCommandArgument;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;

import java.util.List;

/**
 * Represents the Format Raw Command.
 */
public class FormatRaw extends Command {

    /**
     * Creates a new Format Raw Command instance.
     *
     * @param arguments the additional input given upon calling of the command
     * @throws InvalidInputException if the additional input is invalid
     */
    public FormatRaw(String arguments, Text text, TextFormatter textFormatter) throws InvalidInputException {
        super(arguments, new NoCommandArgument(), text, textFormatter);
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
        textFormatter.setFormatRaw();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link SubsequentAction#NONE}
     */
    @Override
    public SubsequentAction getSubsequentAction() {
        return SubsequentAction.NONE;
    }

    /**
     * {@inheritDoc}
     *
     * @return null
     */
    @Override
    public String getOutput() {
        handleIllegalGetOutputCall();
        return null;
    }
}
