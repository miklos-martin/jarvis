package jarvis.jarvis;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list) ListView list;
    @BindView(R.id.input) EditText input;
    private MessageAdapter adapter;
    private final Jarvis jarvis = JarvisFactory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new MessageAdapter(this, new ArrayList<Message>());
        list.setAdapter(adapter);
        adapter.add(new BotMessage(getString(R.string.bot_hi)));
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
        }
    }
}
