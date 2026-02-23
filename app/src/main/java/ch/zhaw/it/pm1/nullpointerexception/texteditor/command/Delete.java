package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.SubsequentAction;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.CommandArgumentWithDefault;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.ParagraphIndexCommandArgument;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;

import java.util.List;

/**
 * Represents the Delete Command.
 */
public class Delete extends Command {

    /**
     * Creates a new Delete Command instance.
     *
     * @param arguments the additional input given upon calling of the command
     * @throws InvalidInputException if the additional input is invalid
     */
    public Delete(String arguments, Text text, TextFormatter textFormatter) throws InvalidInputException {
        super(arguments, new CommandArgumentWithDefault<>(
                        new ParagraphIndexCommandArgument(text), text.size()),
                text, textFormatter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getCorrectUsage() {
        return super.getCorrectUsage() + " [paragraph number (positive integer)]";
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
        text.removeParagraph((int) argument.getParsed());
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
