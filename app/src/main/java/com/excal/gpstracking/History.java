package com.excal.gpstracking;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class History extends AppCompatActivity {

    EditText st1,st2,dt1,dt2;
    SharedPreferences sp,sp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //source lat lon
        sp= getSharedPreferences("sourcelocation",MODE_PRIVATE);
        sp1= getSharedPreferences("lastlocation",MODE_PRIVATE);

        st1=(EditText)findViewById(R.id.solat);
        st2=(EditText)findViewById(R.id.solon);

        dt1=(EditText)findViewById(R.id.destlat);
        dt2=(EditText)findViewById(R.id.destlon);

        st1.setText(sp.getString("lat",""));
        st2.setText(sp.getString("lon",""));
        dt1.setText(sp1.getString("lat",""));
        dt2.setText(sp1.getString("lon",""));

    }
}
