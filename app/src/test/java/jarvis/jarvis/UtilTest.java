package jarvis.jarvis;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class UtilTest {
    @Test
    public void testRandom() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        String picked = Util.random(list);

        assertTrue(picked.equals("a") || picked.equals("b"));
    }

    @Test
    public void notFailsWithEmptyList() {
        assertNull(Util.random(new ArrayList<String>()));
    }

    @Test
    public void testRandomArray() {
        String picked = Util.random(new String[] {"a", "b"});

        assertTrue(picked.equals("a") || picked.equals("b"));
    }

    @Test
    public void notFailsWithEmptyArray() {
        assertNull(Util.random(new String[] {}));
    }
}
