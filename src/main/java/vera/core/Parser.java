package vera.core;

import vera.tasks.Deadline;
import vera.tasks.Event;
import vera.tasks.Task;
import vera.tasks.Todo;

/**
 *  This class is used to convert file-formatted text back into Task objects.
 */

public class Parser {
    /**
     * Converts a file-formatted text into a Task object.
     *
     * @param taskText a line of formatted string retrieved from the file.
     * @return a Task object.
     * @throws IllegalArgumentException If the input format is incorrect or the task type is unrecognised.
     */
    public static Task convertTextToTask(String taskText) throws IllegalArgumentException {
        String[] part = taskText.split(" \\| ");
        if (part.length < 3) {
            throw new IllegalArgumentException("Oops: convert text to task unsuccessful");
        }
        String type = part[0];
        boolean isDone = part[1].equals("1");
        String description = part[2];
        Task task;
        try {
            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (part.length < 4) {
                    throw new IllegalArgumentException("Oops: convert text to Deadline task unsuccessful");
                }
                String by = part[3];
                task = new Deadline(description, by);
                break;
            case "E":
                if (part.length < 5) {
                    throw new IllegalArgumentException("Oops: convert text to Event task unsuccessful");
                }
                String from = part[3];
                String to = part[4];
                task = new Event(description, from, to);
                break;
            default:
                throw new IllegalArgumentException("Oops: Invalid task type");
            }
            loadIsDone(isDone, task);
            return task;
        } catch (VeraException e) {
            throw new IllegalArgumentException("Invalid task format: " + e.getMessage());
        }
    }

    private static void loadIsDone(boolean isDone, Task td) {
        if (isDone) {
            td.markDone();
        }
    }
}
