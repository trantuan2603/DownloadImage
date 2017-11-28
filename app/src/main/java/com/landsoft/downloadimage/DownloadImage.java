package com.landsoft.downloadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by TRANTUAN on 28-Nov-17.
 */

class DownloadImage  extends AsyncTask<String,Integer,Bitmap>{
    ImageView imageView;
    ProgressBar progressBar;
    Integer countTime = 1;

    public DownloadImage(ImageView imageView, ProgressBar progressBar) {
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        String urlPath = strings[0];
        Bitmap bitmap = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(10000);
            connection.connect();
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }

        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        progressBar.setVisibility(View.GONE);
    }
}
