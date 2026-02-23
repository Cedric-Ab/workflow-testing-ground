package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.Indexer;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.SubsequentAction;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments.NoCommandArgument;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the Index Command.
 */
public class Index extends Command {
    String indexDirectoryAsString = null;

    /**
     * Creates a new Index Command instance.
     *
     * @param arguments the additional input given upon calling of the command
     * @throws InvalidInputException if the additional input is invalid
     */
    public Index(String arguments, Text text, TextFormatter textFormatter) throws InvalidInputException {
        super(arguments, new NoCommandArgument(), text, textFormatter);
    }

    @Override
    public String toString() {
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("contentToAdd", indexDirectoryAsString);
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
        Map<String, Set<Integer>> indexDirectory = Indexer.getIndexDirectory(text);

        if (indexDirectory.isEmpty()) {
            indexDirectoryAsString = "No entries found.";
            return;
        }

        int wordDisplayWidth = indexDirectory.keySet().stream()
                .mapToInt(String::length)
                .max()
                .orElse(0) + 3;

        indexDirectoryAsString = indexDirectory.keySet().stream()
                .sorted()
                .map(word -> {
                    String wordResult = indexDirectory.get(word).stream()
                            .sorted()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));

                    return String.format("%-" + wordDisplayWidth + "s", word + ":") + wordResult;
                })
                .collect(Collectors.joining("\n"));
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
     */
    @Override
    public String getOutput() {
        return indexDirectoryAsString;
    }
}
