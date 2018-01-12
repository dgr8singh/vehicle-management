package com.excal.gpstracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
      String file="file:///android_asset/contact.html";
        WebView w=(WebView)findViewById(R.id.web);
        w.loadUrl(file);
    }
}
