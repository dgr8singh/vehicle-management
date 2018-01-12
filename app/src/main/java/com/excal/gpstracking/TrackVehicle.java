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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TrackVehicle extends AppCompatActivity implements OnMapReadyCallback{

    EditText vnum;
    User u;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_vehicle);
        vnum=(EditText)findViewById(R.id.vnum);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void submit(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.TRACK_VEHICLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.isEmpty()) {
                           // Toast.makeText(TrackVehicle.this,response.trim(), Toast.LENGTH_SHORT).show();
                            if(response.trim().equals("Invalid")){
                                Toast.makeText(TrackVehicle.this, "Enter valid vehicle NUmber", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                try {
                                    JSONObject j = new JSONObject(response);
                                    Gson g = new Gson();
                                    User u = g.fromJson(j.toString(), User.class);
                                    if (u != null) {
                                        //Toast.makeText(TrackVehicle.this, u.getLat()+" "+u.getLon(), Toast.LENGTH_SHORT).show();
                                       int n=10;
                                        while(n!=0) {
                                           setMap(Double.parseDouble(u.getLat()), Double.parseDouble(u.getLon()));
                                            n--;
                                       }
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TrackVehicle.this, "Server Error " +error, Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //key must same with server parameter
                params.put("vnum",vnum.getText().toString());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(TrackVehicle.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private  void setMap(double lat,double lon){
        //Toast.makeText(this, "chl gya", Toast.LENGTH_SHORT).show();
        LatLng sydney = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions()
                .position(sydney).title(u.getName())
                .snippet("Current position ")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));

    }
}
