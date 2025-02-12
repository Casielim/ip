package vera.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import vera.core.VeraException;

/**
 * Represents a task with a specific start and end time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an Event object.
     * Converts strings of date and time to datetime type.
     *
     * @param description A line of String describing the Event object.
     * @param from A String of date and time indicating the start time of the event.
     * @param to A String of date and time indicating the end time of the event.
     * @throws VeraException If the input datetime formats are not as expected.
     */
    public Event(String description, String from, String to) throws VeraException {
        super(description);
        this.from = formatDateTime(from);
        this.to = formatDateTime(to);
    }

    private LocalDateTime formatDateTime(String dt) throws VeraException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dt, dtf);
        } catch (DateTimeParseException e) {
            throw new VeraException("Date time input: Use format yyyy-MM-dd HHmm");
        }
    }

    /**
     * Returns a string of the Event object,
     * formatted for user display.
     *
     * @return A formatted string of Event object.
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyyy hhmma");
        return String.format("[E]%s(from: %s to: %s)", super.toString(), from.format(dtf), to.format(dtf));
    }

    /**
     * Returns a string of the Event object,
     * formatted for storing in a file.
     *
     * @return A formatted string of the Event task for file storage.
     */
    @Override
    public String toFileString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "E | " + super.toFileString() + " | " + from.format(dtf) + " | " + to.format(dtf);
    }
}
