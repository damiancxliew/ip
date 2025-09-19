package dabot.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dabot.task.Deadline;
import dabot.task.Event;
import dabot.task.Task;
import dabot.task.Todo;

public class ParserTest {

    @Test
    void parseTaskTodoOk() throws DabotException {
        Task t = Parser.parseTask("todo read book");
        assertTrue(t instanceof Todo);
        assertTrue(t.toString().contains("read book"));
    }

    @Test
    void parseTaskDeadlineIsoDateOk() throws DabotException {
        Task t = Parser.parseTask("deadline return book /by 2019-12-02");
        assertTrue(t instanceof Deadline);
        // Level-8 pretty print or raw fallback both acceptable:
        String s = t.toString();
        assertTrue(s.contains("return book"));
        assertTrue(s.contains("by:"));
    }

    @Test
    void parseTaskEventMixedDatesOk() throws DabotException {
        Task t = Parser.parseTask("event project meeting /from 2019-12-02 /to 4pm");
        assertTrue(t instanceof Event);
        String s = t.toString();
        assertTrue(s.contains("project meeting"));
        assertTrue(s.contains("from:"));
        assertTrue(s.contains("to:"));
    }

    @Test
    void parseIndex1ValidOk() throws DabotException {
        int idx1 = Parser.parseIndex1("mark   7");
        assertEquals(7, idx1);
    }

    @Test
    void parseIndex1MissingThrows() {
        assertThrows(DabotException.class, () -> Parser.parseIndex1("mark"));
    }

    @Test
    void parseOnDateIsoOk() throws DabotException {
        LocalDate d = Parser.parseOnDate("on 2019-10-15");
        assertEquals(LocalDate.of(2019, 10, 15), d);
    }

    @Test
    void parseOnDateBadThrows() {
        assertThrows(DabotException.class, () -> Parser.parseOnDate("on Oct-15-2019"));
    }
}
