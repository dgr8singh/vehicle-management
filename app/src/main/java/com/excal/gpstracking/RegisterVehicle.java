package com.excal.gpstracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class RegisterVehicle extends AppCompatActivity {

    EditText name,uname,pass,date,vnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);
        name=(EditText) findViewById(R.id.name);
        uname=(EditText) findViewById(R.id.uname);
        pass=(EditText) findViewById(R.id.pass);
        date=(EditText) findViewById(R.id.date);
        vnum=(EditText) findViewById(R.id.vnum);
    }
    public  void submit(View v){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.isEmpty()) {
                            //Toast.makeText(RegisterVehicle.this,response, Toast.LENGTH_SHORT).show();
                            if(response.trim().equals("success")) {
                                Toast.makeText(RegisterVehicle.this, "Registration Succesful", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(RegisterVehicle.this, Admin.class);
                                startActivity(in);
                                finish();
                            }
                            else if(response.trim().equals("blank")){
                                Toast.makeText(RegisterVehicle.this, "Please Enter details ", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(RegisterVehicle.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterVehicle.this, "Server Error " + error, Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //key must same with server parameter
                params.put("name", name.getText().toString());
                params.put("uname", uname.getText().toString());
                params.put("pass", pass.getText().toString());
                params.put("dates", date.getText().toString());
                params.put("vnum", vnum.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterVehicle.this);
        requestQueue.add(stringRequest);


    }
}
