package ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidUsageException;

import java.util.Objects;

public class StringFurtherInput implements FurtherInput<String> {
    private final String prompt;
    private String value;

    protected StringFurtherInput(String prompt) {
        this.prompt = Objects.requireNonNull(prompt, "prompt must not be null");
    }

    @Override
    public boolean isResolved() {
        return value != null;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    protected String processInput(String input) throws InvalidUsageException {
        return input;
    }

    @Override
    public void resolve(String input) throws InvalidUsageException {
        if (isResolved()) {
            throw new IllegalStateException("Further input already resolved");
        }
        Objects.requireNonNull(input, "input must not be null");

        System.out.println(processInput(input));
        this.value = processInput(input);
    }

    @Override
    public String getValue() {
        if (!isResolved()) {
            throw new IllegalStateException("Further input not yet resolved");
        }

        return value;
    }

    @Override
    public String toString() {
        return "StringFurtherInput(prompt=\"%s\", value=\"%s\")".formatted(prompt, value);
    }
}
