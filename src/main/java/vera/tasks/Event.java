package vera.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import vera.core.VeraException;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event (String description, String from, String to) throws VeraException {
        super(description);
        this.from = formatDateTime(from);
        this.to = formatDateTime(to);
    }

    private LocalDateTime formatDateTime (String dt) throws VeraException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dt, dtf);
        } catch (DateTimeParseException e) {
            throw new VeraException("Date time input: Use format yyyy-MM-dd HHmm");
        }
    }
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyyy hhmma");
        return String.format("[E]%s(from: %s to: %s)", super.toString(), from.format(dtf), to.format(dtf));
    }

    @Override
    public String toFileString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "E | " + super.toFileString() + " | " + from.format(dtf) + " | " + to.format(dtf);
    }
}
