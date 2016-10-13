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
}
