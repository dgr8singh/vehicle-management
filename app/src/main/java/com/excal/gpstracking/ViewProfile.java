package com.excal.gpstracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ViewProfile extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Bundle b=getIntent().getExtras();
        u=(User)b.getSerializable("user");
        t1=(TextView)findViewById(R.id.dname);
        t2=(TextView)findViewById(R.id.duname);
        t3=(TextView)findViewById(R.id.dvnum);
        t4=(TextView)findViewById(R.id.dpnum);
        t5=(TextView)findViewById(R.id.dpass);

        t1.setText(u.getName());
        t2.setText(u.getUname());
        t3.setText(u.getV_number());
        t4.setText(u.getDates());
        t5.setText(u.getPass());

    }
}
