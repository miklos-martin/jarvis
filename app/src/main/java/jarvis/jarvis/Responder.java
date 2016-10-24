package jarvis.jarvis;

public interface Responder {
    public boolean supports(HumanMessage message);
    public BotMessage respond(HumanMessage message);
}
