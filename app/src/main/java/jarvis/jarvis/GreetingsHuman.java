package jarvis.jarvis;

import java.util.ArrayList;
import java.util.List;
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
        List<String> greetings = new ArrayList<>();
        greetings.add("Hola");
        greetings.add("Hi!");
        greetings.add("Well, hello there!");
        greetings.add("Yeah..");

        return new BotMessage(Util.random(greetings));
    }
}
