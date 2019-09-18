package Model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A Log class which creates a unique logger for each class which will all write to one file.
 */
public class Log {

    private static final String FILE_NAME = "log.txt"; // File name and path.
    private static final boolean append = true; // Whether to append to current log.txt
    private static Logger logger;   // The current logger.
    private static FileHandler fileHandler; // FileHandler to print to file.
    private static SimpleFormatter simpleFormatter; // Used to format the log simply.

    static {
        try {
            // Delete (if already there) and create file.
            File file = new File(FILE_NAME);
            if (file.exists() && !file.isDirectory()) {
                file.delete();
            }
            file.createNewFile();

            fileHandler = new FileHandler(FILE_NAME, append);
            simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method which gets a unique logger for each class and makes it so that the logger
     * will append to log.txt.
     *
     * @param name Name of the logger (usually the class name).
     * @return The logger for this class which will append to log.txt
     */
    public static Logger getLogger(String name){
        logger = Logger.getLogger(name);
        // Add our fileHandler to the logger if it is not there already.
        if (!(Arrays.asList(logger.getHandlers()).contains(fileHandler))) {
            logger.addHandler(fileHandler);

            // Stop the logger from using the default console handler.
            logger.setUseParentHandlers(false);
        }
        return logger;
    }
}
