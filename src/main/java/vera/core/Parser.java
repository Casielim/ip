package vera.core;

import vera.tasks.Task;
import vera.tasks.Todo;
import vera.tasks.Deadline;
import vera.tasks.Event;

public class Parser {
    public Parser() {
    }

    public static Task convertTextToTask(String taskText) throws IllegalArgumentException {
        String[] part = taskText.split(" \\| ");
        if (part.length < 3) {
            throw new IllegalArgumentException("Error: convert text to task unsuccessful");
        }
        String type = part[0];
        boolean isDone = part[1].equals("1");
        String description = part[2];

        switch (type) {
        case "T":
            Task td = new Todo(description);
            if (isDone) {
                td.markDone();
            } else {
                td.unmarkDone();
            }
            return td;
        case "D":
            if (part.length < 4) {
                throw new IllegalArgumentException("Error: convert text to Deadline task unsuccessful");
            }
            String by = part[3];
            try {
                Task dl = new Deadline(description, by);
                if (isDone) {
                    dl.markDone();
                } else {
                    dl.unmarkDone();
                }
                return dl;
            } catch (VeraException e) {
                System.out.println(e.getMessage());
            }
        case "E":
            if (part.length < 5) {
                throw new IllegalArgumentException("Error: convert text to Event task unsuccessful");
            }
            String from = part[3];
            String to = part[4];
            try {
                Task ev = new Event(description, from, to);
                if (isDone) {
                    ev.markDone();
                } else {
                    ev.unmarkDone();
                }
                return ev;
            } catch (VeraException e) {
                System.out.println(e.getMessage());
            }

        default:
            throw new IllegalArgumentException("Error: Invalid task type");
        }
    }
}
