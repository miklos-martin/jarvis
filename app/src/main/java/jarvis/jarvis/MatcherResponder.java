package jarvis.jarvis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MatcherResponder implements Responder {
    @Override
    public boolean supports(HumanMessage message) {
        return match(message).find();
    }

    @Override
    public BotMessage respond(HumanMessage message) {
        Matcher matcher = match(message);
        matcher.find();

        return doRespond(message, matcher);
    }

    protected abstract BotMessage doRespond(HumanMessage message, Matcher matcher);

    private Matcher match(HumanMessage message) {
        Pattern pattern = getPattern();
        Matcher matcher = pattern.matcher(message.getContent());

        return matcher;
    }

    protected abstract Pattern getPattern();
}
