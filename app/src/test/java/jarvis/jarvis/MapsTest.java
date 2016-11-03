package jarvis.jarvis;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class MapsTest {
    @Test
    public void supportsSpecificQuestions() {
        String[] examples = new String[] {
                "how do I go to hell on foot?",
                "how do I go to budapest by car?",
                "how do I go to budapest",
        };

        for (String example : examples) {
            assertTrue(new Maps().supports(new HumanMessage(example)));
        }
    }

    @Test
    public void doesntSupportAnything() {
        String[] examples = new String[] {
                "how do I blabla go to hell on foot?",
                "how go to budapest by car?",
        };

        for (String example : examples) {
            assertFalse(new Maps().supports(new HumanMessage(example)));
        }
    }

    @Test
    public void respondsAppropriately() {
        HumanMessage msg = new HumanMessage("how do I go to hell on foot?");
        ImageMessage response = (ImageMessage) new Maps().respond(msg);

        assertEquals("Here's how you go to hell on foot", response.getSayable());
        assertTrue(response.hasIntent());
        assertEquals("google.navigation:q=hell&mode=w", response.getIntent().uri);

        try {
            new URL(response.getContent());
        } catch (MalformedURLException e) {
            fail("Not a valid url: " + response.getContent());
        }
    }

    @Test
    public void respondsWithoutModeToo() {
        HumanMessage msg = new HumanMessage("how do I go to hell?");
        ImageMessage response = (ImageMessage) new Maps().respond(msg);

        assertEquals("Here's how you go to hell", response.getSayable());
        assertEquals("google.navigation:q=hell", response.getIntent().uri);
    }
}
