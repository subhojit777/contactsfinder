package com.subhojitpaul.contactsfinder;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class ShowMapActivity extends Activity {
    private double deviceLatitude;
    private double deviceLongitude;
    private LocationManager locationManager;
    private String provider;
    private GoogleMap map;
    private ArrayList<double[]> locations;
    private static final String TAG = "ContactsFinder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        Intent intent = getIntent();
        Log.d(TAG, "I made it here");

        showLocations();
    }

    public double[] getDeviceLocation() {
        Log.d(TAG, "I made it here too");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d(TAG, "I made it here too should i be here");

        Criteria criteria = new Criteria();
        Log.d(TAG, "I made it here too should i be here or here");
        provider = locationManager.getBestProvider(criteria, false);
        Log.d(TAG, "I made it here too should i be here or here man");
        Location location = locationManager.getLastKnownLocation(provider);
        Log.d(TAG, "I made it here too should i be here or here man man");
        Log.d(TAG, String.valueOf(location.getLatitude()));
        Log.d(TAG, String.valueOf(location.getLongitude()));

        return new double[] {location.getLatitude(), location.getLongitude()};
    }

    public double[] getAnotherLocation() {
        Log.d(TAG, "I made it here again");
        return new double[] {22.569165, 88.433597};
    }

    public void showLocations() {
        Log.d(TAG, "I made it here finally");
        ArrayList locations = new ArrayList();
        locations.add(getDeviceLocation());
        locations.add(getAnotherLocation());

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if (map != null) {
            for (int i = 0; i < locations.size(); i++) {
                locations.get(i);
                Marker currentDeviceLocation = map.addMarker(new MarkerOptions().position(new LatLng(22.569165, 88.433597)));
            }
        }
    }
}
