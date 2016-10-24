package jarvis.jarvis;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChuckNorrisTest {
    private final HumanMessage supportedMsg = new HumanMessage("string contains chuck");

    @Test
    public void supportsChuck() {
        ChuckNorris responder = new ChuckNorris(new Util());
        String[] supportedExamples = new String[] {
                "contains chuck somewhere",
                "ends with chuck",
                "contains chuck norris somewhere",
                "contains Chuck Norris somewhere",
                "CHUCK Norris"
        };
        for (String supportedExample : supportedExamples) {
            assertTrue(responder.supports(new HumanMessage(supportedExample)));
        }

        assertFalse(responder.supports(new HumanMessage("anything")));
    }

    @Test
    public void fetchesJokes() {
        ChuckNorris responder = new ChuckNorris(new MockUtil());
        BotMessage response = responder.respond(supportedMsg);

        assertEquals("Chuck Norris doesn't play god. Playing is for children.", response.getContent());
    }

    private class MockUtil extends Util {
        @Override
        public String http(String targetUrl) {
            // http://api.icndb.com/jokes/166
            return "{ \"type\": \"success\", \"value\": { \"id\": 166, \"joke\": \"Chuck Norris doesn't play god. Playing is for children.\", \"categories\": [] } }";
        }
    }
}
