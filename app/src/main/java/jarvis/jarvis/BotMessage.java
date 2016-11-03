package jarvis.jarvis;

public class BotMessage extends Message {
    private String sayable;

    public BotMessage(String content) {
        super(content, false);
        sayable = content;
    }

    public BotMessage(String content, String sayable) {
        super(content, false);
        this.sayable = sayable;
    }

    public BotMessage(String content, String sayable, IntentDescriptor intent) {
        super(content, false, intent);
        this.sayable = sayable;
    }

    public String getSayable() {
        return sayable;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((BotMessage) obj).sayable.equals(sayable);
    }

    @Override
    public String toString() {
        return super.toString() + " say:" + sayable;
    }
}
