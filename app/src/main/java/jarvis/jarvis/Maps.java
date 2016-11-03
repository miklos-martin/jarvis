package jarvis.jarvis;

import android.content.Intent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Maps extends MatcherResponder {
    private static final String SAYABLE_PREFIX = "Here's how you go to ";
    private static final String MAPS_URL_PREFIX = "https://maps.googleapis.com/maps/api/staticmap?zoom=17&size=600x600&markers=color:blue";

    @Override
    protected BotMessage doRespond(HumanMessage message, Matcher matcher) {
        String expression = matcher.group(1);
        String target = matcher.group(2);
        String mode = matcher.group(4);

        if (target == null) target = expression;

        String imageSrc = buildImageSrc(target);
        String sayable = buildSayable(expression);
        IntentDescriptor intent = buildIntent(target, mode);

        return new ImageMessage(imageSrc, sayable, intent);
    }

    private String buildImageSrc(String target) {
        return MAPS_URL_PREFIX + encode("|" + target);
    }

    private String buildSayable(String expression) {
        return SAYABLE_PREFIX + expression;
    }

    private IntentDescriptor buildIntent(String target, String mode) {
        String uri = "google.navigation:q=" + encode(target);

        if (mode != null) {
            uri += "&mode=" + getMode(mode);
        }

        return new IntentDescriptor(
                Intent.ACTION_VIEW,
                uri,
                "com.google.android.apps.maps"
        );
    }

    private String getMode(String mode) {
        if (mode.equals("foot")) return "w";
        else return "d";
    }

    @Override
    protected Pattern getPattern() {
        return Pattern.compile("^how do i go to ((.+) (on|by) (foot|car)|([^?]+))", Pattern.CASE_INSENSITIVE);
    }

    private String encode(String input) {
        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
