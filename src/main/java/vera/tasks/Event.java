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
        if (isFromBeforeTo(formatDateTime(from), formatDateTime(to))) {
            this.from = formatDateTime(from);
            this.to = formatDateTime(to);
        }
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
     * Snoozes or updates date time easily.
     *
     * @param newFrom Updated From time.
     * @param newTo Udated To time.
     */
    public void snooze(String newFrom, String newTo) throws VeraException {
        LocalDateTime from = formatDateTime(newFrom);
        LocalDateTime to = formatDateTime(newTo);
        if (isFromBeforeTo(from, to)) {
            this.from = LocalDateTime.parse(newFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            this.to = LocalDateTime.parse(newTo, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        }
    }

    private boolean isFromBeforeTo(LocalDateTime from, LocalDateTime to) throws VeraException {
        if (from.isBefore(to)) {
            return true;
        } else {
            throw new VeraException("from datetime should be before to datetime");
        }
    }

    /**
     * Returns a string of the Event object, formatted for user display.
     *
     * @return A formatted string of Event object.
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyyy hhmma");
        return String.format("[E]%s(from: %s to: %s)", super.toString(), from.format(dtf), to.format(dtf));
    }

    /**
     * Returns a string of the Event object, formatted for storing in a file.
     *
     * @return A formatted string of the Event task for file storage.
     */
    @Override
    public String toFileString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "E | " + super.toFileString() + " | " + from.format(dtf) + " | " + to.format(dtf);
    }
}
