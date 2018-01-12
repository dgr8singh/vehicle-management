package com.excal.gpstracking;

import android.content.Intent;
import android.location.SettingInjectorService;
import android.media.audiofx.BassBoost;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        /*String file="file:///android_asset/help.html";
        WebView w=(WebView)findViewById(R.id.helpweb);
        w.loadUrl(file);*/

    }
}
