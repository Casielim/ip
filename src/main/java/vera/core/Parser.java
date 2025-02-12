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

        switch (type) {
        case "T":
            Task td = new Todo(description);
            if (isDone) {
                td.markDone();
            }
            return td;
        case "D":
            if (part.length < 4) {
                throw new IllegalArgumentException("Oops: convert text to Deadline task unsuccessful");
            }
            String by = part[3];
            try {
                Task dl = new Deadline(description, by);
                if (isDone) {
                    dl.markDone();
                }
                return dl;
            } catch (VeraException e) {
                throw new IllegalArgumentException("Invalid deadline format: " + e.getMessage());
            }
        case "E":
            if (part.length < 5) {
                throw new IllegalArgumentException("Oops: convert text to Event task unsuccessful");
            }
            String from = part[3];
            String to = part[4];
            try {
                Task ev = new Event(description, from, to);
                if (isDone) {
                    ev.markDone();
                }
                return ev;
            } catch (VeraException e) {
                throw new IllegalArgumentException("Invalid event format: " + e.getMessage());
            }

        default:
            throw new IllegalArgumentException("Oops: Invalid task type");
        }
    }
}
