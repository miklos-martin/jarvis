package jarvis.jarvis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private static final int HUMAN = 0, BOT = 1, IMAGE = 2;
    private static final int[] layouts = new int[] { R.layout.human, R.layout.bot, R.layout.image };

    public MessageAdapter(Context context, List<Message> objects) {
        super(context, R.layout.human, objects);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        int layout = layouts[type];
        Message message = getItem(position);

        if (message == null) return super.getView(position, convertView, parent);

        convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);

        if (type == IMAGE) handleImage(convertView, message);
        else handleText(convertView, message);

        handleIntent(convertView, message);

        return convertView;
    }

    private void handleImage(View convertView, Message message) {
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        new DownloadTask(imageView).execute(message.getContent());
    }

    private void handleText(View convertView, Message message) {
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(message.getContent());
    }

    private void handleIntent(View convertView, Message message) {
        if (message.hasIntent()) {
            convertView
                    .findViewById(R.id.container)
                    .setOnClickListener(new IntentListener(message.getIntent()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message item = getItem(position);
        if (item.isHuman()) return HUMAN;
        else if (item instanceof ImageMessage) return IMAGE;
        else return BOT;
    }

    private class DownloadTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public DownloadTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                InputStream in = new URL(urls[0]).openStream();
                return BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private class IntentListener implements View.OnClickListener {
        private IntentDescriptor intentDescriptor;

        public IntentListener(IntentDescriptor intentDescriptor) {
            this.intentDescriptor = intentDescriptor;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(intentDescriptor.action, intentDescriptor.uri);
            intent.setPackage(intentDescriptor.packageName);
            getContext().startActivity(intent);
            System.out.println("klikkkkk");
        }
    }
}
