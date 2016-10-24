package jarvis.jarvis;

import com.google.gson.Gson;

public class ChuckNorris implements Responder {
    private Util util;

    public ChuckNorris(Util util) {
        this.util = util;
    }

    @Override
    public boolean supports(HumanMessage message) {
        return message.getContent().toLowerCase().matches("(.*)chuck\\s*(norris)?(.*)");
    }

    @Override
    public BotMessage respond(HumanMessage msg) {
        String resp = util.http("http://api.icndb.com/jokes/random");
        ChuckResponse r = new Gson().fromJson(resp, ChuckResponse.class);
        return new BotMessage(r.value.joke.replace("&quot;", "'"));
    }

    public class ChuckResponse {
        public Joke value;
    }
    public class Joke {
        public String joke;
    }
}
