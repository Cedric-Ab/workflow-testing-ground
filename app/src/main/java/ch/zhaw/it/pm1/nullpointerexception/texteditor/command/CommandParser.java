package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible to "translate" commands from string to the matching command object.
 */
public class CommandParser {
    public static final SortedMap<String, CommandFactory> COMMAND_FACTORIES;
    private static final Logger logger = Logger.getLogger(CommandParser.class.getName());
    private static final Map<String, Pattern> commandRegexCache = new HashMap<>();

    static {
        SortedMap<String, CommandFactory> modifiableCommands = new TreeMap<>(
                Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()).reversed());

        modifiableCommands.put("exit", Exit::new);
        modifiableCommands.put("quit", Exit::new);
        modifiableCommands.put("print", Print::new);
        modifiableCommands.put("add", Add::new);
        modifiableCommands.put("dummy", Dummy::new);
        modifiableCommands.put("delete", Delete::new);
        modifiableCommands.put("del", Delete::new);
        modifiableCommands.put("replace", Replace::new);
        modifiableCommands.put("index", Index::new);
        modifiableCommands.put("format raw", FormatRaw::new);
        modifiableCommands.put("format fix", FormatFix::new);

        COMMAND_FACTORIES = Collections.unmodifiableSortedMap(modifiableCommands);
    }

    private final Text text;
    private final TextFormatter textFormatter;

    public CommandParser(Text text, TextFormatter textFormatter) {
        this.text = text;
        this.textFormatter = textFormatter;
    }

    /**
     * Returns a regex Pattern for the given command key, allowing flexible spaces.
     * <p>
     * Pattern: {@code ^(commandKey)(?:\s+)?(.*)$}
     *
     * @param commandKey the command key
     * @return the compiled Pattern
     */
    private static Pattern getCommandRegex(String commandKey) {
        return commandRegexCache.computeIfAbsent(commandKey, commandKey1 -> {
            String flexible = commandKey1.replace(" ", "\\s*"); // allow any amount of spaces
            return Pattern.compile("^(" + flexible + ")(?:\\s+)?(.*)$", Pattern.CASE_INSENSITIVE);
        });
    }

    /**
     * Returns a matching command object with additional input based on the input string.
     *
     * @param input string representation of a command
     * @return matching command object
     * @throws InvalidInputException when a matching command couldn't be found or additional input is invalid
     */
    public Command getCommand(String input) throws InvalidInputException {
        String cleanedInput = Objects.requireNonNull(input, "input must not be null").trim();

        for (String commandKey : COMMAND_FACTORIES.sequencedKeySet()) {
            Matcher matcher = getCommandRegex(commandKey).matcher(cleanedInput);
            if (matcher.matches()) {
                String remainingInput = matcher.group(2).trim();

                logger.fine(String.format(
                        "Matched to command: `%s` with remaining input: \"%s\"", commandKey, remainingInput));
                return COMMAND_FACTORIES.get(commandKey).create(remainingInput, text, textFormatter);
            }
        }
        throw new InvalidInputException("No matching command found", input,
                "Available commands are: " +
                        String.join(", ", COMMAND_FACTORIES.keySet().stream().sorted().toList()));
    }
}
