package vera.tasks;

/**
 * Represents a task with a description and a completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Construct a Task object with the task description.
     * Sets the task as not yet done.
     *
     * @param description A string message describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task not yet done.
     */
    public void unmarkDone() {
        this.isDone = false;
    }

    /**
     * Returns "X" if the task is done else return empty space.
     *
     * @return "X" or empty space based on th isDone status.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); //mark done task with X
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }

    /**
     * Converts a task into a formatted string suitable for file storage.
     *
     * @return A string representation of the task for file storage.
     */
    public String toFileString() {
        return String.format("%s | %s", isDone ? "1" : "0", description);
    }
}
