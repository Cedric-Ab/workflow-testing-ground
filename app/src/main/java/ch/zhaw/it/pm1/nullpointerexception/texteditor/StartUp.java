package ch.zhaw.it.pm1.nullpointerexception.texteditor;

import java.io.File;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Entry point of the application.
 * Creates an instance of the App and starts the main processing loop.
 */
public class StartUp {
    private static final Logger logger = Logger.getLogger(StartUp.class.getName());

    private StartUp() {
        // Prevent instantiation
    }

    private static void loggingInit() {
        File logDirectory = new File("logs");
        //noinspection ResultOfMethodCallIgnored
        logDirectory.mkdirs();
        try {
            InputStream inputStream = StartUp.class.getClassLoader().getResourceAsStream("logging.properties");

            if (inputStream == null) {
                throw new RuntimeException("logging.properties not found");
            }

            // Load properties into memory
            java.util.Properties props = new java.util.Properties();
            props.load(inputStream);

            // Replace FileHandler pattern with a timestamped filename
            String timestamp = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            props.setProperty("java.util.logging.FileHandler.pattern", logDirectory + "/texteditor_" + timestamp + ".log");

            // Convert back to InputStream and load into LogManager
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            props.store(baos, null);
            InputStream modifiedInputStream = new java.io.ByteArrayInputStream(baos.toByteArray());

            LogManager.getLogManager().readConfiguration(modifiedInputStream);

        } catch (Exception e) {
            throw new RuntimeException("Logging init failed", e);
        }
    }

    /**
     * Starts the text editor application.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        loggingInit();

        logger.info("Starting application...");
        App app = new App();
        app.run();
    }
}
