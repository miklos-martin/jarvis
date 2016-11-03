package jarvis.jarvis;

public abstract class Message {
    private String content = "";
    private boolean isHuman = true;
    private IntentDescriptor intent;

    public Message(String content, boolean isHuman) {
        this.content = content;
        this.isHuman = isHuman;
    }

    public Message(String content, boolean isHuman, IntentDescriptor intent) {
        this.content = content;
        this.isHuman = isHuman;
        this.intent = intent;
    }

    public String getContent() {
        return content;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public IntentDescriptor getIntent() {
        return intent;
    }

    public boolean hasIntent() {
        return null != intent;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Message && ((Message) obj).content.equals(content);
    }

    @Override
    public String toString() {
        return content;
    }
}
