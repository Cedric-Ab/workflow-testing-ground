package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Provides utilities for processing words in the context of indexing text.
 */
public final class Indexer {
    public static final String VALID_STARTING_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ";
    public static final String VALID_ENDING_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZäöüÄÖÜ0123456789";
    public static final int INDEX_DIRECTORY_MIN_WORD_COUNT = 4;
    private static final Logger logger = Logger.getLogger(Indexer.class.getName());


    private Indexer() {
        // Prevent instantiation
    }

    /**
     * Builds and returns an index directory over all paragraphs.
     *
     * @return Index directory of the entire text
     */
    public static Map<String, Set<Integer>> getIndexDirectory(Text text) {
        logger.fine("Start building final index directory...");
        Map<String, List<Integer>> indexDirectoryWithList = new HashMap<>();
        for (int i = 1; i <= text.size(); i++) {
            Paragraph paragraph = text.getParagraph(i);
            for (Map.Entry<String, Integer> entry : extractWordsFromParagraph(paragraph).entrySet()) {
                for (int j = 0; j < entry.getValue(); j++) {
                    indexDirectoryWithList.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(i);
                }
            }
        }

        Map<String, Set<Integer>> result = indexDirectoryWithList.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= INDEX_DIRECTORY_MIN_WORD_COUNT)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Set.copyOf(e.getValue())
                ));
        logger.fine("Finished building index directory: " + indexDirectoryWithList);
        return result;
    }

    // package-private for testing
    static Map<String, Integer> extractWordsFromParagraph(Paragraph paragraph) {
        List<String> words = extractWords(paragraph.getContent());

        Map<String, Integer> directory = new HashMap<>();

        for (String word : words) {
            directory.putIfAbsent(word, 0);
            directory.put(word, directory.get(word) + 1);
        }

        return directory;
    }

    /**
     * Returns a list of valid words extracted from the given paragraph content.
     *
     * @param paragraphContent the content of the paragraph
     * @return a list of valid words
     */
    // package-private for testing
    static List<String> extractWords(String paragraphContent) {
        Objects.requireNonNull(paragraphContent, "paragraphContent must not be null");

        List<String> words = new ArrayList<>();
        if (paragraphContent.isEmpty()) {
            return words;
        }

        String[] rawWords = paragraphContent.split("\\s+");
        for (String rawWord : rawWords) {
            String trimmedWord = trimWord(rawWord);

            if (isValidWord(trimmedWord)) {
                words.add(trimmedWord);
            } else {
                logger.finest("Invalid word skipped: " + rawWord);
            }
        }
        return words;
    }

    /**
     * Trims any invalid ending characters from the given word.
     * A valid ending character is defined in {@link #VALID_ENDING_CHARACTERS}.
     *
     * @param word the word to trim
     * @return the trimmed word
     */
    // package-private for testing
    static String trimWord(String word) {
        String trimmedWord = Objects.requireNonNull(word, "word must not be null").trim();

        while (!trimmedWord.isEmpty()
                && !VALID_ENDING_CHARACTERS.contains(String.valueOf(trimmedWord.charAt(trimmedWord.length() - 1)))) {
            trimmedWord = trimmedWord.substring(0, trimmedWord.length() - 1);
        }
        return trimmedWord;
    }

    /**
     * Checks whether the given word is valid.
     * A valid word has more than 1 character
     * and starts with a {@link #VALID_STARTING_CHARACTERS} and ends with a {@link #VALID_ENDING_CHARACTERS}.
     *
     * @param word the word to check
     * @return if the word is valid
     */
    public static boolean isValidWord(String word) {
        Objects.requireNonNull(word, "word must not be null");

        if (word.length() <= 1) return false;

        boolean hasValidStart = VALID_STARTING_CHARACTERS.contains(String.valueOf(word.charAt(0)));
        boolean hasValidEnd = VALID_ENDING_CHARACTERS.contains(String.valueOf(word.charAt(word.length() - 1)));

        return hasValidStart && hasValidEnd;
    }
}
