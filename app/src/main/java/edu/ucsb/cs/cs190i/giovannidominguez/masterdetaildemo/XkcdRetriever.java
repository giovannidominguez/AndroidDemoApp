package edu.ucsb.cs.cs190i.giovannidominguez.masterdetaildemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Samuel on 4/20/2017.
 */

public class XkcdRetriever {
    public static final int XkcdMaxId = 1827;
    private static final String ImageUrlRegex = "(https://imgs\\.xkcd\\.com/comics/.*)";
    private static final Pattern ImageUrlPattern = Pattern.compile(ImageUrlRegex);

    public static void getImage(int id, final XkcdBitmapResultListener listener) {
        XkcdRetrieveImageTask retrieveImageTask = new XkcdRetrieveImageTask(listener);
        retrieveImageTask.execute(id);
    }

    interface XkcdBitmapResultListener {
        void onImage(Bitmap image);
    }

    private static class XkcdRetrieveImageTask extends AsyncTask<Integer, Void, Bitmap> {
        private XkcdBitmapResultListener listener;

        public XkcdRetrieveImageTask(XkcdBitmapResultListener listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            try {
                URL pageUrl = new URL("https://xkcd.com/" + params[0]);
                URLConnection connection = pageUrl.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder pageHtmlBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    pageHtmlBuilder.append(inputLine);
                    pageHtmlBuilder.append('\n');
                }
                reader.close();
                Matcher matcher = ImageUrlPattern.matcher(pageHtmlBuilder.toString());
                if (matcher.find()) {
                    URL imageUrl = new URL(matcher.group(1));
                    URLConnection imageConnection = imageUrl.openConnection();
                    return BitmapFactory.decodeStream(imageConnection.getInputStream());
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (listener != null) {
                listener.onImage(bitmap);
            }
        }
    }
}
