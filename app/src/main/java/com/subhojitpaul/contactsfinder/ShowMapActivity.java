package com.subhojitpaul.contactsfinder;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowMapActivity extends Activity implements LocationListener {
    private Double deviceLatitude;
    private Double deviceLongitude;
    private LocationManager locationManager;
    private String provider;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        Intent intent = getIntent();
        String range = intent.getStringExtra(MainActivity.RANGE);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        }
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
        deviceLatitude = location.getLatitude();
        deviceLongitude = location.getLongitude();

        Marker currentDeviceLocation = map.addMarker(new MarkerOptions().position(new LatLng(deviceLatitude, deviceLongitude)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(deviceLatitude, deviceLongitude), 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
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
}
