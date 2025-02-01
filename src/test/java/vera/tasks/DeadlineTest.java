package vera.tasks;

import org.junit.jupiter.api.Test;
import vera.core.VeraException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeadlineTest {
    @Test
    public void markTaskTest() throws VeraException {
        Deadline dlTask = new Deadline("deadline return book", "2025-02-01 2359");
        assertFalse(dlTask.isDone);
        dlTask.markFeature();
        assertTrue(dlTask.isDone);
    }

    @Test
    public void toStringTest() throws VeraException {
        Deadline dlTask = new Deadline("return book ", "2025-02-01 2359");
        assertEquals("[D][ ] return book (by: Feb 01 2025 1159pm)", dlTask.toString(), "toString() format is incorrect");

        dlTask.markFeature();
        assertEquals("[D][X] return book (by: Feb 01 2025 1159pm)", dlTask.toString(), "toString() format is incorrect after markDone");

        dlTask.unmarkFeature();
        assertEquals("[D][ ] return book (by: Feb 01 2025 1159pm)", dlTask.toString(), "toString() format is incorrect after unmarkDone");
    }


}
