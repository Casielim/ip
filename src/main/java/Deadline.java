import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;

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

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyyy hhmma");
        return "[D]" + super.toString() + " (by: " + by.format(dtf) + ")";
    }

    @Override
    public String toFileString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "D | " + super.toFileString() + " | " + by.format(dtf);
    }
}
