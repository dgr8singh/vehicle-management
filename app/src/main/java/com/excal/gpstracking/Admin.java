package com.excal.gpstracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

    }
    public void register(View v) {
        Intent in = new Intent(this, RegisterVehicle.class);
        startActivity(in);

    }
    public void track(View v) {
        Intent in = new Intent(this, TrackVehicle.class);
        startActivity(in);
    }
}
