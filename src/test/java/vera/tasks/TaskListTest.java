package vera.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import vera.core.VeraException;
import vera.ui.Ui;

public class TaskListTest {
    @Test
    public void addTaskTest() throws VeraException {
        Ui ui = new Ui();
        TaskList tl = new TaskList();
        tl.addTask("todo borrow book");

        assertEquals("borrow book", tl.getTask(0).description);
    }

    @Test
    public void deleteTaskTest() throws VeraException {
        Ui ui = new Ui();
        List<Task> list = new ArrayList<>();
        list.add(new Todo("borrow book"));
        list.add(new Todo("return book"));
        TaskList taskList = new TaskList(list);

        taskList.deleteTask(0);

        assertEquals(1, list.size());
        assertEquals("return book", list.get(0).description);
    }
}
