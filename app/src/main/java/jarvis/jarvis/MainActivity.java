package jarvis.jarvis;

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
        list.setSelection(adapter.getCount() - 1);
    }

    private void sendMessage(String message) {
        adapter.add(new HumanMessage(message));
        adapter.add(new BotMessage(message));
    }
}
