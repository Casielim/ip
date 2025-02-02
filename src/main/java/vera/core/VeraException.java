package vera.core;

/**
 * Represents a custom exception for handling errors to Vera chatbot.
 */
public class VeraException extends Exception {
    /**
     * Constructs a VeraException with an error message.
     *
     * @param message The detail describing the error.
     */
    public VeraException(String message) {
        super(message);
    }
}
