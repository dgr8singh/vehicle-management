package com.excal.gpstracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    User u;
    EditText t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Bundle b=getIntent().getExtras();
        u=(User)b.getSerializable("user");
        t1=(EditText)findViewById(R.id.op);
        t2=(EditText)findViewById(R.id.np);
        t3=(EditText)findViewById(R.id.cp);
    }

    public void submit(View v){
if(valid()){
changePass();
}
    }

    private boolean valid(){

        if(!t1.getText().toString().equals(u.getPass())){
            //Toast.makeText(this, "Old Password not match !", Toast.LENGTH_SHORT).show();
            t1.setError("Old Password not match !");
            t1.requestFocus();
            return false;
        }else if(!t2.getText().toString().equals(t3.getText().toString())){
            Toast.makeText(this, "new and confirm Password not match !", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }

    private void changePass(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.CHANGE_PASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            Toast.makeText(ChangePassword.this, response.trim(), Toast.LENGTH_SHORT).show();
                            if(response.trim().equals("Successfully Change Password")){
                                Intent in = new Intent(ChangePassword.this, LoginActivity.class);
                                startActivity(in);
                                finish();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangePassword.this, "Server Error " +error, Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //key must same with server parameter
                params.put("uname",u.getUname());
                params.put("pass",t3.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
        requestQueue.add(stringRequest);

    }

}
