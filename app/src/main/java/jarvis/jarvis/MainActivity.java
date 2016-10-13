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

    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.et_message) EditText editTextMessage;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new MessageAdapter(this, new ArrayList<Message>());
        listView.setAdapter(adapter);
        adapter.add(new BotMessage(getString(R.string.bot_hi)));
    }

    @OnClick(R.id.btn_send)
    public void onSend() {
        String message = editTextMessage.getText().toString();
        sendMessage(message);
        editTextMessage.setText("");
        listView.setSelection(adapter.getCount() - 1);
    }

    private void sendMessage(String message) {
        adapter.add(new HumanMessage(message));
        adapter.add(new BotMessage(message));
    }
}
