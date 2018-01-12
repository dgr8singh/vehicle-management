package com.excal.gpstracking;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    EditText t1,t2;
    Button b,b1;
    String lat="29.948151",lon="76.815490";
    BroadcastReceiver br=null;
    int counter=0;
    private GoogleMap mMap;
    @Override
    protected void onResume() {
        super.onResume();
        if(br==null){
        br=new BroadcastReceiver() {
    @Override
      public void onReceive(Context context, Intent intent) {
       // Toast.makeText(context, intent.getExtras().get("coordinate").toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"Hello start", Toast.LENGTH_LONG).show();
        lat=intent.getExtras().getString("lat");
       lon=intent.getExtras().getString("lon");
    //server code here
        if(lat!=null&&lon!=null){
            counter=1;
            setMap(Double.parseDouble(lat),Double.parseDouble(lon));
            Log.e("location","update");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.LOCATION_UPDATE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                         Toast.makeText(MainActivity.this, response.trim(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Server Error " +error, Toast.LENGTH_SHORT).show();

                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    //key must same with server parameter
                    params.put("lat",lat);
                    params.put("lon",lon);
                    params.put("uname",uname);
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);

            }
           }
          };
        }

   if(counter==1){
    SharedPreferences.Editor e=sp.edit();
    e.putString("lat",lat);
    e.putString("lon",lon);
    e.commit();
   }

        registerReceiver(br,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(br!=null){
         unregisterReceiver(br);
        }
    }
    int coun=1;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
        mMap.addMarker(new MarkerOptions()
                .position(sydney).title(u.getV_number())
                .snippet(coun + " Point ")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));
        coun++;
    }

    private  void setMap(double lat,double lon){

        LatLng sydney = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions()
                .position(sydney).title(u.getV_number())
                .snippet(coun + " Point ")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       // mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));
        coun++;
    }

    String uname;
    User u;
    SharedPreferences sp;
    SharedPreferences sp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         sp= getSharedPreferences("sourcelocation",MODE_PRIVATE);
         sp1= getSharedPreferences("lastlocation",MODE_PRIVATE);
         u=(User) getIntent().getExtras().getSerializable("user");
         uname=u.getUname();
        if(!runtimePermission()) {
           enableButtons();
        }
        //setMap(Double.parseDouble(lat),Double.parseDouble(lon));
        SharedPreferences.Editor e=sp.edit();
        e.putString("lat",lat);
        e.putString("lon",lon);
        e.commit();
    }

    public void enableButtons(){
        b1 = (Button) findViewById(R.id.logout);
        Intent in = new Intent(MainActivity.this, MyLocation.class);
        startService(in);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e = sp1.edit();
                e.putString("lat", lat);
                e.putString("lon", lon);
                e.commit();
                Intent in = new Intent(MainActivity.this, MyLocation.class);
                stopService(in);
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED
         && grantResults[1]==PackageManager.PERMISSION_GRANTED ){
                enableButtons();
              }else{
                runtimePermission();
            }
        }
    }

    public boolean runtimePermission(){
       if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
       {
           requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
           return true;
       }
       else{
          return false;
           }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.aboutus:
                startActivity(new Intent(MainActivity.this,About.class));
                break;

            case R.id.viewprofile:
                Intent ii1=new Intent(MainActivity.this,ViewProfile.class);
                ii1.putExtra("user",u);
                startActivity(ii1);
            break;

            case R.id.contactus:{
               Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234567890"));
                startActivity(intent);
                break;}

            case R.id.help:{
                Intent intent=new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;}

            case R.id.changepass:
                Intent ii=new Intent(MainActivity.this,ChangePassword.class);
                ii.putExtra("user",u);
                startActivity(ii);
                break;
            case R.id.history:
                Intent i=new Intent(MainActivity.this,History.class);
                startActivity(i);
                break;

        }

        return true;
    }
}
