package vera.tasks;

import org.junit.jupiter.api.Test;
import vera.core.VeraException;
import vera.core.Ui;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void addTaskTest() throws VeraException {
        Ui ui = new Ui();
        TaskList tl = new TaskList(ui);
        tl.addTask("todo borrow book");

        assertEquals("borrow book", tl.getTask(0).description);
    }

    @Test
    public void deleteTaskTest() {
        Ui ui = new Ui();
        List<Task> list = new ArrayList<>();
        list.add(new Todo("borrow book"));
        list.add(new Todo("return book"));
        TaskList taskList = new TaskList(list, ui);

        taskList.deleteTask(0);

        assertEquals(1, list.size());
        assertEquals("return book", list.get(0).description);
    }
}
