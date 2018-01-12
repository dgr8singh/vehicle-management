package com.excal.gpstracking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashFile extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide(); //hide action bar
        }
        catch (NullPointerException e){}
        // hide notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_file);
        String file="file:///android_asset/slide.html";
        WebView w=(WebView)findViewById(R.id.aboutweb);
        w.loadUrl(file);
        WebSettings webSettings=w.getSettings();
        webSettings.setJavaScriptEnabled(true);
        w.setWebViewClient(new WebViewClient());
        Thread background = new Thread(new Runnable() {
            public void run() {
                try {
                  Thread.sleep(6000);
                    Intent in=new Intent(SplashFile.this,LoginActivity.class);
                    startActivity(in);
                    finish();
                } catch (Throwable t) {
                    // just end the background thread
                    Log.i("Animation", "Thread  exception " + t);
                }
            }
        });
        background.start();
    }
}
