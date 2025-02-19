package vera.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import vera.core.VeraException;

/**
 * Represents a task with a specific deadline.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructs a Deadline object.
     * Converts a string of date and time to datetime type.
     *
     * @param description A line of String describing the Deadline object.
     * @param by A String of date and time.
     * @throws VeraException If the input datetime format is not as expected.
     */
    public Deadline(String description, String by) throws VeraException {
        super(description);
        this.by = formatDateTime(by);
    }

    private LocalDateTime formatDateTime(String dt) throws VeraException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            this.by = LocalDateTime.parse(dt, dtf);
            return this.by;
        } catch (DateTimeParseException e) {
            throw new VeraException("Date time input: Use format yyyy-MM-dd HHmm");
        }
    }

    /**
     * Snoozes or updates the date time easily.
     *
     * @param newBy Updated date time.
     */
    public void snooze(String newBy) {
        this.by = LocalDateTime.parse(newBy, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Returns a string of the Deadline object, formatted for user display.
     *
     * @return A formatted string of Deadline object.
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyyy hhmma");
        return "[D]" + super.toString() + "(by: " + by.format(dtf) + ")";
    }

    /**
     * Returns a string of the Deadline object, formatted for storing in a file.
     *
     * @return A formatted string of the Deadline task for file storage.
     */
    @Override
    public String toFileString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "D | " + super.toFileString() + " | " + by.format(dtf);
    }
}
