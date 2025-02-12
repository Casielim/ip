package vera.tasks;

/**
 * Represents a task to be done.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo object.
     *
     * @param description A line of String describing the Deadline object.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string of the Todo object,
     * formatted for user display.
     *
     * @return A formatted string of Todo object.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string of the Todo object,
     * formatted for storing in a file.
     *
     * @return A formatted string of the Todo task for file storage.
     */
    @Override
    public String toFileString() {
        return "T | " + super.toFileString();
    }
}
