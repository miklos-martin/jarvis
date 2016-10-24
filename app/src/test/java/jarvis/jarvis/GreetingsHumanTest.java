package jarvis.jarvis;

import org.junit.Test;

import static org.junit.Assert.*;

public class GreetingsHumanTest {

    private final Responder greeter = new GreetingsHuman();

    @Test
    public void testSupportsHi() {
        for (String content : supportedContentExamples()) {
            assertTrue(greeter.supports(new HumanMessage(content)));
        }
    }

    private String[] supportedContentExamples() {
        return new String[] {
                "hi",
                "hi jarvis",
                "HI JARVIS",
                "hello",
                "hello!",
                "hey, Jarvis",
                "hi dude"
        };
    }

    @Test
    public void doesntSupportAnythingElse() {
        assertFalse(greeter.supports(new HumanMessage("whatever")));
    }

    @Test
    public void greetsFriendly() {
        BotMessage response = greeter.respond(new HumanMessage("hi dude"));
        assertNotNull(response);
    }
}
