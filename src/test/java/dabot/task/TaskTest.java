package dabot.task;

import dabot.main.DabotException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void testTodoEncodeDecode() throws DabotException {
        Task t = new Todo("read book");
        String line = t.encodeString();
        Task decoded = Task.decodeString(line);
        assertEquals(t.toString(), decoded.toString());
    }

    @Test
    void testDeadlineEncodeDecodeIso() throws DabotException {
        Task t = new Deadline("return book", "2019-12-02");
        String line = t.encodeString();
        Task decoded = Task.decodeString(line);
        assertEquals(t.toString(), decoded.toString());
    }

    @Test
    void testEventEncodeDecodeRaw() throws DabotException {
        Task t = new Event("project meeting", "Mon 2pm", "4pm");
        String line = t.encodeString();
        Task decoded = Task.decodeString(line);
        assertEquals(t.toString(), decoded.toString());
    }

    @Test
    void testDecodeMalformedLineThrows() {
        assertThrows(DabotException.class, () -> Task.decodeString("E | 0 | only two fields"));
    }
}
