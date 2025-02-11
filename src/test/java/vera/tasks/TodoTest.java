package vera.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TodoTest {
    @Test
    public void todoConstructorTest() {
        Todo todoTask = new Todo("borrow book"); // ✅ Initialized in test method
        assertEquals("borrow book", todoTask.description);
    }

    @Test
    public void markTaskTest() {
        Todo todoTask = new Todo("borrow book");
        todoTask.markDone();
        assertTrue(todoTask.isDone);
    }

    @Test
    public void unmarkTaskTest() {
        Todo todoTask = new Todo("borrow book");
        todoTask.markDone();
        todoTask.unmarkDone();
        assertFalse(todoTask.isDone);
    }

    @Test
    public void toStringTest() {
        Todo todoTask = new Todo("exercise");
        assertEquals("[T][ ] exercise", todoTask.toString(), "toString() format is incorrect");

        todoTask.markDone();
        assertEquals("[T][X] exercise", todoTask.toString(), "toString() format is incorrect after markDone");

        todoTask.unmarkDone();
        assertEquals("[T][ ] exercise", todoTask.toString(), "toString() format is incorrect after unmarkDone");
    }

    public void toFileStringTest() {
        Todo todoTask = new Todo("do homework");
        assertEquals("T | 0 | do homework", todoTask.toFileString(), "toFileString() format is incorrect");

        todoTask.markDone();
        assertEquals("T | 1 | do homework", todoTask.toFileString(), "toString() format is incorrect after markDone");

        todoTask.unmarkDone();
        assertEquals("T | 0 | do homework", todoTask.toFileString(), "toString() format is incorrect after unmarkDone");
    }
}
