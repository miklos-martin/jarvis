package jarvis.jarvis;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.junit.Assert.*;

public class HowDoITest {

    private final HowDoI howDoI = new HowDoI(new Util());
    private final HumanMessage humanMessage = new HumanMessage("how do i asd?");
    private final String searchphrase = "site:stackoverflow.com asd";
    private final BotMessage emptyMessage = new BotMessage(HowDoI.DEFAULT_REPLY);

    @Test
    public void supportsSpecificQuestions() {
        String[] examples = new String[] {
                "how do i do this and that?",
                "HOW do i do this and that?",
                "HOW do i do this and that",
        };

        for (String example : examples) {
            assertTrue(howDoI.supports(new HumanMessage(example)));
        }
    }

    @Test
    public void doesntSupportRandomMessages() {
        String[] examples = new String[] {
                " do i do this and that",
                "how do i",
        };

        for (String example : examples) {
            assertFalse(howDoI.supports(new HumanMessage(example)));
        }
    }

    @Test
    public void searchesProperly() {
        Responses responses = new Responses(searchphrase, "");
        MockUtil util = new MockUtil(responses);
        BotMessage response = new HowDoI(util).respond(humanMessage);
        assertEquals(1, util.called);

        assertEquals(emptyMessage, response);
    }

    @Test
    public void loadsStackoverflow() {
        Responses responses = validSearch();
        MockUtil util = new MockUtil(responses);
        BotMessage response = new HowDoI(util).respond(humanMessage);
        assertEquals(2, util.called);

        assertEquals(emptyMessage, response);
    }

    @Test
    public void extractsAnswer() {
        Responses responses = validSearch();
        responses.sr = "<html><div class=\"answer\">...<div class=\"post-text\">ANSWER</div></div></html>";

        MockUtil util = new MockUtil(responses);
        BotMessage response = new HowDoI(util).respond(humanMessage);
        assertEquals(2, util.called);

        assertEquals(new BotMessage("ANSWER", HowDoI.SAY_THIS), response);
    }

    @Test
    public void extractsCodeFromAnswer() {
        Responses responses = validSearch();
        responses.sr = "<html><div class=\"answer\">...<div class=\"post-text\">..<pre><code>CODE</code></pre>...</div></div></html>";

        MockUtil util = new MockUtil(responses);
        BotMessage response = new HowDoI(util).respond(humanMessage);
        assertEquals(2, util.called);

        assertEquals(new BotMessage("CODE", HowDoI.SAY_THIS), response);
    }

    @NonNull
    private Responses validSearch() {
        return new Responses(
                searchphrase,
                "<html><h3 class=\"r\"><a href=\"SOURL\">asd</a></h3></html>",
                "SOURL",
                ""
        );
    }

    private class Responses {
        public String googleSearchPhrase;
        public String googleResponse;
        public String surl;
        public String sr;

        public Responses(String googleSearchPhrase, String googleResponse) {
            try {
                this.googleSearchPhrase = HowDoI.GOOGLE_PREFIX + URLEncoder.encode(googleSearchPhrase, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            this.googleResponse = googleResponse;
        }

        public Responses(String gsp, String gr, String surl, String sr) {
            this(gsp, gr);
            this.surl = surl;
            this.sr = sr;
        }
    }

    private class MockUtil extends Util {

        public int called = 0;
        private Responses responses;

        public MockUtil(Responses responses) {
            this.responses = responses;
        }

        @Override
        public String http(String targetUrl) {
            called += 1;

            if (called == 1) {
                assertEquals(responses.googleSearchPhrase, targetUrl);

                return responses.googleResponse;
            }

            assertEquals(responses.surl, targetUrl);

            return responses.sr;
        }
    }
}
