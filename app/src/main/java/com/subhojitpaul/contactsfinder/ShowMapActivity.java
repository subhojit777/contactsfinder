package com.subhojitpaul.contactsfinder;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ShowMapActivity extends Activity implements LocationListener {
    private Double deviceLatitude;
    private Double deviceLongitude;
    private LocationManager locationManager;
    private String provider;
    private GoogleMap map;
    private List<Double[]> locations = new ArrayList<Double[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        Intent intent = getIntent();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        }

        showLocations();
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

    public Double[] getAnotherLocation() {
        return new Double[]{28.63224, 77.22072};
    }

    public void showLocations() {
        Double[] coordinates;
        locations.add(new Double[]{deviceLatitude, deviceLongitude});
        locations.add(getAnotherLocation());

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if (map != null) {
            for (int i = 0; i < locations.size(); i++) {
                coordinates = locations.get(i);
                Marker currentDeviceLocation = map.addMarker(new MarkerOptions().position(new LatLng(coordinates[0], coordinates[1])));
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        deviceLatitude = location.getLatitude();
        deviceLongitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
