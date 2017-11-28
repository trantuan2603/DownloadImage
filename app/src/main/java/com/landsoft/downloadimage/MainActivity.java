package com.landsoft.downloadimage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgView;
    Button btnDownload;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.img_view);
        btnDownload = findViewById(R.id.btn_download);
        progressBar = findViewById(R.id.progress_bar);

        btnDownload.setOnClickListener(this);
        checkAndRequestPermissions();
    }

    // xin quyen
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    //kiem tra da ket noi internet chua
    public boolean checkInternetConnec(){
        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View view) {

            if (checkInternetConnec()){
                DownloadImage downloadImage = new DownloadImage(imgView,progressBar);
                String url = "http://2.bp.blogspot.com/-wW9hEXOAl5g/VPv1J8b76fI/AAAAAAAAEFY/Qy6xOr-M80k/s1600/ANDROID.png";
                downloadImage.execute(url);
            }else Toast.makeText(this, "No connect Internet",Toast.LENGTH_SHORT).show();
    }
}
