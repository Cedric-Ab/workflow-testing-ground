package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidUsageException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.StringValidator;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.SubsequentAction;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.CommandArgumentWithDefault;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.ParagraphIndexCommandArgument;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.StringFurtherInput;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents the Replace Command.
 */
public class Replace extends Command {
    private final FurtherInput<String> searchText = new StringFurtherInput("Please enter search text:") {
        @Override
        public String processInput(String input) throws InvalidUsageException {
            if (input.isBlank()) {
                throw new InvalidUsageException("Search text must not be empty.", input, "search text");
            }
            if (StringValidator.containsInvalidCharacters(input)) {
                throw new InvalidUsageException("Input contains invalid Characters", input, "search text");
            }
            return input;
        }
    };
    private final FurtherInput<String> replaceText = new StringFurtherInput("Please enter replacement text:") {
        @Override
        public String processInput(String input) throws InvalidUsageException {
            if (StringValidator.containsInvalidCharacters(input)) {
                throw new InvalidUsageException("Input contains invalid Characters", input, "replace text");
            }
            return input;
        }
    };

    /**
     * Creates a new Replace Command instance.
     *
     * @param arguments optional paragraph number ("n") or empty string
     * @throws InvalidInputException if the paragraph specification is invalid
     */
    public Replace(String arguments, Text text, TextFormatter textFormatter) throws InvalidInputException {
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
    public String toString() {
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("searchText", searchText.toString());
        parameters.put("replaceText", replaceText.toString());
        return constructToString(parameters);
    }

    @Override
    protected List<FurtherInput<?>> getAllFurtherInputs() {
        return List.of(searchText, replaceText);
    }

    /**
     * Performs the actual replacement operation on the chosen paragraph.
     */
    @Override
    public void executeCommand() {
        text.getParagraph((int) argument.getParsed()).replace(searchText.getValue(), replaceText.getValue());
    }

    /**
     * Replace does not trigger any follow-up action.
     *
     * @return {@link SubsequentAction#NONE}
     */
    @Override
    public SubsequentAction getSubsequentAction() {
        return SubsequentAction.NONE;
    }

    /**
     * Replace makes no textual output.
     *
     * @return never returns normally; always triggers error handling
     */
    @Override
    public String getOutput() {
        handleIllegalGetOutputCall();
        return null;
    }
}
