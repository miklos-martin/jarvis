package jarvis.jarvis;

import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.send)
    public void onSend() {
        String message = input.getText().toString();
        if (message.isEmpty()) return;

        sendMessage(message);
        input.setText("");
    }

    private void sendMessage(String message) {
        HumanMessage msg = new HumanMessage(message);
        adapter.add(msg);

        new RespondTask().execute(msg);
    }

    private class RespondTask extends AsyncTask<HumanMessage, Void, BotMessage> {
        @Override
        protected BotMessage doInBackground(HumanMessage... params) {
            return jarvis.respond(params[0]);
        }

        @Override
        protected void onPostExecute(BotMessage botMessage) {
            adapter.add(botMessage);
            list.setSelection(adapter.getCount() - 1);
            tts.speak(botMessage.getContent(), TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private class TextToSpeechInitializer implements TextToSpeech.OnInitListener {
        @Override
        public void onInit(int status) {
            tts.setLanguage(Locale.getDefault());
            tts.setPitch(1.1f);
            new RespondTask().execute(new HumanMessage("hi"));
        }
    }
}
