package jarvis.jarvis;

public abstract class Message {
    private String content = "";
    private boolean isHuman = true;

    public Message(String content, boolean isHuman) {
        this.content = content;
        this.isHuman = isHuman;
    }

    public String getContent() {
        return content;
    }

    public boolean isHuman() {
        return isHuman;
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
