package jarvis.jarvis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class Util {
    public String http(String targetUrl) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    public static <T> T random(List<T> list) {
        if (list.size() > 0) return list.get(new Random().nextInt(list.size()));
        else return null;
    }

    public static <T> T random(T[] list) {
        if (list.length > 0) return list[new Random().nextInt(list.length)];
        else return null;
    }
}
