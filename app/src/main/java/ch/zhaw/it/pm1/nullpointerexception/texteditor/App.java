package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.Command;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.CommandParser;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Main execution class controlling the editor loop.
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    private final UserInterface userInterface = new UserInterface();
    private final Text text = new Text();
    private final TextFormatter textFormatter = new TextFormatter();
    private final CommandParser commandParser = new CommandParser(text, textFormatter);

    private boolean running = true;

    /**
     * Runs the text editor program.
     */
    public void run() {
        while (running) {
            Command command = getNextCommand();

            logger.info("Executing command: " + command);

            handleFurtherInput(command);
            command.executeCommand();
            handleSubsequentAction(command);

            logger.info("Finished executing command: " + command);
        }
    }

    private void printInvalidInputFeedback(InvalidInputException e) {
        logger.warning("InvalidInputException occured: " + e);

        String userInformation = String.format("Invalid Input: %s%n%s", e.getMessage(), e.getUserInformation());
        userInterface.printError(userInformation + "\n");
    }

    private Command getNextCommand() {
        logger.fine("Getting the next command");

        Command command = null;

        while (command == null) {
            userInterface.printOutput("Please enter a command.\n");

            String userInput = userInterface.getUserInput();

            try {
                command = commandParser.getCommand(userInput);
            } catch (InvalidInputException e) {
                printInvalidInputFeedback(e);
            }
        }

        return command;
    }

    private void handleFurtherInput(Command command) {
        for (Optional<FurtherInput<?>> nextInput = command.getNextFurtherInput();
             nextInput.isPresent();
             nextInput = command.getNextFurtherInput()
        ) {
            logger.fine("Handling further input: " + nextInput.get());

            boolean success = false;
            while (!success) {
                userInterface.printOutput(nextInput.get().getPrompt() + " ");
                try {
                    nextInput.get().resolve(userInterface.getUserInput());
                    success = true;
                } catch (InvalidInputException e) {
                    printInvalidInputFeedback(e);
                }
            }
        }
    }

    private void exit() {
        running = false;
        logger.fine("Termination initiated");
    }

    private void handleSubsequentAction(Command command) {
        switch (command.getSubsequentAction()) {
            case NONE -> logger.fine("No subsequent action to execute");
            case EXIT -> exit();
            case OUTPUT -> userInterface.printOutput(command.getOutput() + "\n");
            default -> throw new IllegalStateException("Unexpected value: " + command.getSubsequentAction());
        }
    }
}
