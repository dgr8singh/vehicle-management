package com.excal.gpstracking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by DEEP SINGH on 25-04-2017.
 */

public class MyLocation extends Service{

    LocationManager locationmanager;
    LocationListener locationListener;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
       // Toast.makeText(this, "service created", Toast.LENGTH_SHORT).show();
        Log.e("welcome","service on create");
        locationListener=new LocationListener() {
           @Override
           public void onLocationChanged(Location location) {
           Intent in=new Intent("location_update");
               in.putExtra("lat",String.valueOf(location.getLatitude()));
               in.putExtra("lon",String.valueOf(location.getLongitude()));
               sendBroadcast(in);
           }

           @Override
           public void onStatusChanged(String s, int i, Bundle bundle) {

           }

           @Override
           public void onProviderEnabled(String s) {

           }

           @Override
           public void onProviderDisabled(String s) {
              Intent in=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(in);
           }
       };
        locationmanager=(LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,locationListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationmanager!=null){
//noinspection MissingPermission
            locationmanager.removeUpdates(locationListener);
        }
    }
}
