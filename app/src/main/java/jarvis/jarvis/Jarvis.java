package jarvis.jarvis;


import java.util.ArrayList;
import java.util.List;

public class Jarvis implements Responder {
    public static final String DEFAULT_RESPONSE = "What's wrong with you, human?";
    private List<Responder> responders = new ArrayList<Responder>();

    public void add(Responder responder) {
        responders.add(responder);
    }

    @Override
    public boolean supports(HumanMessage message) {
        return true;
    }

    @Override
    public BotMessage respond(HumanMessage message) {
        for (Responder responder : responders)
            if (responder.supports(message)) return responder.respond(message);

        return new BotMessage(DEFAULT_RESPONSE);
    }
}
