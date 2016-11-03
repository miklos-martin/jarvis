package jarvis.jarvis;

public class IntentDescriptor {
    public final String action;
    public final String uri;
    public final String packageName;

    public IntentDescriptor(String action, String uri, String packageName) {
        this.action = action;
        this.uri = uri;
        this.packageName = packageName;
    }
}
