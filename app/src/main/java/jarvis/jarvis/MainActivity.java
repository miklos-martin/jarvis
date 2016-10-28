package jarvis.jarvis;

import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list) ListView list;
    @BindView(R.id.input) EditText input;
    private MessageAdapter adapter;
    private final Jarvis jarvis = JarvisFactory.create();
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new MessageAdapter(this, new ArrayList<Message>());
        list.setAdapter(adapter);
        tts = new TextToSpeech(this, new TextToSpeechInitializer());

    }

    @OnEditorAction(R.id.input)
    public boolean onEditorAction() {
        onSend();
        return true;
    }

    @OnClick(R.id.send)
    public void onSend() {
        String message = input.getText().toString();
        if (message.isEmpty()) return;

        humanSay(message);
        input.setText("");
    }

    private void humanSay(String message) {
        HumanMessage msg = new HumanMessage(message);
        adapter.add(msg);

        new RespondTask().execute(msg);
    }

    private void botSay(BotMessage msg) {
        adapter.add(msg);
        tts.speak(msg.getSayable(), TextToSpeech.QUEUE_FLUSH, null, null);
        list.setSelection(adapter.getCount() - 1);
    }

    private class RespondTask extends AsyncTask<HumanMessage, Void, BotMessage> {
        @Override
        protected BotMessage doInBackground(HumanMessage... params) {
            return jarvis.respond(params[0]);
        }

        @Override
        protected void onPostExecute(BotMessage botMessage) {
            botSay(botMessage);
        }
    }

    private class TextToSpeechInitializer implements TextToSpeech.OnInitListener {
        private static final String VOICE_NAME = "en-gb-x-fis#male_3-local";

        @Override
        public void onInit(int status) {
            tts.setLanguage(Locale.getDefault());
            try {
                for (Voice voice :  tts.getVoices()) {
                    if (voice.getName().equals(VOICE_NAME)) {
                        tts.setVoice(voice);
                        break;
                    }
                }
            } catch (NullPointerException e) {}

            new RespondTask().execute(new HumanMessage("hi"));
        }
    }
}
