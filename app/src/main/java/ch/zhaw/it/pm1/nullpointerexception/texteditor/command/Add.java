package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.*;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.CommandArgumentWithDefault;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.ParagraphIndexCommandArgument;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.StringFurtherInput;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents the Add Command.
 */
public class Add extends Command {
    protected final FurtherInput<String> contentToAdd = new StringFurtherInput("Enter text for new paragraph:") {
        @Override
        public String processInput(String input) {
            return StringValidator.filterInvalidCharacters(input);
        }
    };

    /**
     * Creates a new Add Command instance.
     *
     * @param arguments the additional input given upon calling of the command
     * @throws InvalidInputException if the additional input is invalid
     */
    public Add(String arguments, Text text, TextFormatter textFormatter) throws InvalidInputException {
        super(arguments, new CommandArgumentWithDefault<>(
                        new ParagraphIndexCommandArgument(text, 1), text.size() + 1),
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
    public String toString() {
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("contentToAdd", contentToAdd.toString());
        return constructToString(parameters);
    }

    @Override
    protected List<FurtherInput<?>> getAllFurtherInputs() {
        return List.of(contentToAdd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCommand() {
        Paragraph paragraph = new Paragraph(contentToAdd.getValue());
        text.addParagraph((int) argument.getParsed(), paragraph);
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
