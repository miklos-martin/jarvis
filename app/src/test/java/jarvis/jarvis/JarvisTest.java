package jarvis.jarvis;

import android.support.annotation.NonNull;

import org.junit.*;
import static org.junit.Assert.*;

public class JarvisTest {
    private Jarvis jarvis;

    @Before
    public void setUp() {
        jarvis = new Jarvis();
    }

    @Test
    public void testSupportsAnything() {
        assertTrue(jarvis.supports(new HumanMessage("gibberish")));
    }

    @Test
    public void respondsWithDefaultMessage() {
        assertEquals(
                new BotMessage(Jarvis.DEFAULT_RESPONSE),
                jarvis.respond(new HumanMessage("whatever"))
        );
    }

    @Test
    public void callsSupportingResponder() {
        final BotMessage response = new BotMessage("yay");
        jarvis.add(supportingResponder(response));

        assertEquals(response, jarvis.respond(new HumanMessage("whatever")));
    }

    @Test
    public void usesFirstSupportingResponder() {
        final BotMessage response = new BotMessage("yayyay");

        jarvis.add(usupportingResponder());
        jarvis.add(supportingResponder(response));

        assertEquals(response, jarvis.respond(new HumanMessage("whatever")));
    }

    @NonNull
    private Responder supportingResponder(final BotMessage response) {
        return new Responder() {
            @Override
            public boolean supports(HumanMessage message) {
                return true;
            }

            @Override
            public BotMessage respond(HumanMessage message) {
                return response;
            }
        };
    }

    @NonNull
    private Responder usupportingResponder() {
        return new Responder() {
            @Override
            public boolean supports(HumanMessage message) {
                return false;
            }

            @Override
            public BotMessage respond(HumanMessage message) {
                return null;
            }
        };
    }
}

