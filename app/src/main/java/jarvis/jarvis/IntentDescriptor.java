package jarvis.jarvis;

import android.net.Uri;

public class IntentDescriptor {
    public final String action;
    public final Uri uri;
    public final String packageName;

    public IntentDescriptor(String action, Uri uri, String packageName) {
        this.action = action;
        this.uri = uri;
        this.packageName = packageName;
    }
}
