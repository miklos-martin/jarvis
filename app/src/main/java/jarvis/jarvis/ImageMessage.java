package jarvis.jarvis;

public class ImageMessage extends BotMessage {
    public ImageMessage(String content, String sayable) {
        super(content, sayable);
    }

    public ImageMessage(String content, String sayable, IntentDescriptor intent) {
        super(content, sayable, intent);
    }
}
