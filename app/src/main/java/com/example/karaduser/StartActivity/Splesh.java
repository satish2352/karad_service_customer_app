package com.example.karaduser.StartActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.karaduser.MainActivity;
import com.example.karaduser.R;
import com.example.karaduser.my_library.CheckNetwork;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;
import com.google.android.material.snackbar.Snackbar;

public class Splesh extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    private String version;
    private String versionName;
    private TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splesh_main);

        try {
            versionName = Splesh.this.getPackageManager()
                    .getPackageInfo(Splesh.this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = versionName;
//        tv_version=findViewById(R.id.tv_version);
//        tv_version.setText("V"+version);

        if (CheckNetwork.isInternetAvailable(Splesh.this)) //returns true if internet available
        {
            // noConnectionLayout.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Splesh.this.CONNECTIVITY_SERVICE);

                    if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        if (Shared_Preferences.getPrefs(Splesh.this, Constants.REG_ID) != null) {
                            Log.d("reg_id", "run: " + Shared_Preferences.getPrefs(Splesh.this, Constants.REG_ID));

                            startActivity(new Intent(Splesh.this, MainActivity.class));
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        } else {
                            startActivity(new Intent(Splesh.this, Login.class));
                            finish();
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        }
                    }
                }


            }, SPLASH_TIME_OUT);
        }
        else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            check();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .setDuration(50000)
                    .show();

        }


    }

    private void check() {
        if (CheckNetwork.isInternetAvailable(Splesh.this)) //returns true if internet available
        {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Splesh.this.CONNECTIVITY_SERVICE);

                    if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        if (Shared_Preferences.getPrefs(Splesh.this, Constants.REG_ID) != null) {
                            Log.d("reg_id", "run: " + Shared_Preferences.getPrefs(Splesh.this, Constants.REG_ID));
                            startActivity(new Intent(Splesh.this, MainActivity.class));
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        } else {

                            startActivity(new Intent(Splesh.this, Login.class));
                            finish();
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        }
                    }
                }


            }, SPLASH_TIME_OUT);
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            check();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .setDuration(50000)
                    .show();

        }
    }



}
