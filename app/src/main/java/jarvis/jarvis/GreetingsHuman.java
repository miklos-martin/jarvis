package jarvis.jarvis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GreetingsHuman implements Responder {
    @Override
    public boolean supports(HumanMessage message) {
        Pattern p = Pattern.compile("^(hi|hello|hey)([,! a-z]*)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(message.getContent());

        return matcher.find();
    }

    @Override
    public BotMessage respond(HumanMessage message) {
        String[] greetings = new String[] {
                "Hey human!",
                "Well, hello there!",
                "What's up?",
                "Do you have something to say?"
        };

        return new BotMessage(Util.random(greetings));
    }
}
