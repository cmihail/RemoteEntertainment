package logger;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Defines a wrapper for {@link Logger} with a given format.
 * TODO(cmihail): explain the format and has some problems
 * TODO(cmihail): add class name as param for logging
 *
 * @author cmihail
 */
public class CommonLogger {

  private static Logger logger = null; // TODO(cmihail): maybe use a file

  private static void initiate() {
    logger = Logger.getLogger("");

    // Remove any existing handlers.
    for (Handler handler : logger.getHandlers()) { // TODO(cmihail): test this
      logger.removeHandler(handler);
    }

    // Add logger handler with a given formatting.
    logger.addHandler(new Handler() {
      @Override
      public void publish(LogRecord record) {
        Level level = record.getLevel();
        System.out.println("[Client] (" + record.getSourceClassName() + ", "
            + level.getName() + "): " + record.getMessage());
        if (level == Level.SEVERE) {
          System.exit(1);
        }
      }

      @Override
      public void flush() {
        System.out.flush();
      }

      @Override
      public void close() throws SecurityException {
        // Nothing to do
      }
    });
  }

  /**
   * @param level the message level
   * @param message the message itself
   */
  public static void log(Level level, String message) {
    if (logger == null) {
      initiate();
    }
    logger.log(level, message);
  }

  /**
   * @param level the message level
   * @param e the exception that must be printed
   */
  public static void logException(Level level, Exception e) {
    if (logger == null) {
      initiate();
    }
    logger.log(level, e.getMessage());
    e.printStackTrace();
    // e.printStackTrace(CommonLogger.getPrintStream()); // TODO(cmihail): doesn't print anything
  }
}
