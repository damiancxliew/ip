package dabot.main;

/**
 * Represents exceptions specific to DaBot.
 * <p>
 * Used to signal errors in parsing commands,
 * task operations, or file I/O.
 * </p>
 */
public class DabotException extends Exception {

    /**
     * Creates a new {@code DabotException} with a message.
     *
     * @param message the error message
     */
    public DabotException(String message) {
        super(message);
    }
}
