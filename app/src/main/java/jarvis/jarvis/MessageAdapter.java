package jarvis.jarvis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private static final int HUMAN = 0, BOT = 1;

    public MessageAdapter(Context context, List<Message> objects) {
        super(context, R.layout.item_human_message, objects);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Message item = getItem(position);
        if (item.isHuman()) return HUMAN;
        else return BOT;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        int layout;

        if (viewType == HUMAN) {
            layout = R.layout.item_human_message;
        } else {
            layout = R.layout.item_bot_message;
        }

        convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(getItem(position).getContent());

        return convertView;
    }
}
