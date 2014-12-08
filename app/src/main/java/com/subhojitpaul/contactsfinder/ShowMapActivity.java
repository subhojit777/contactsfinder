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

public class ShowMapActivity extends Activity implements LocationListener {
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

        showLocations();
    }

    public double[] getDeviceLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        return new double[] {location.getLatitude(), location.getLongitude()};
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "here");
        deviceLatitude = location.getLatitude();
        deviceLongitude = location.getLongitude();

        double[] deviceLocation = new double[] {deviceLatitude, deviceLongitude};
        double[] anotherLocation = getAnotherLocation();

        locations.add(anotherLocation);
        locations.add(deviceLocation);

        showLocations();

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(deviceLatitude, deviceLongitude), 15));
        //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        // Auto-generated stub.
    }

    @Override
    public void onProviderEnabled(String s) {
        // Auto-generated stub.
    }

    @Override
    public void onProviderDisabled(String s) {
        // Auto-generated stub.
    }

    public double[] getAnotherLocation() {
        return new double[] {22.569165, 88.433597};
    }

    public void showLocations() {
        ArrayList locations = new ArrayList();
        locations.add(getDeviceLocation());
        locations.add(getAnotherLocation());

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if (map != null) {
            for (int i = 0; i < locations.size(); i++) {
                locations.get(i);
                //Marker currentDeviceLocation = map.addMarker(new MarkerOptions().position(locations.get(i)[0], locations.get(i)[1]));
            }
        }
    }
}
