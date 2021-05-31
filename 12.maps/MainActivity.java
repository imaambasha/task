package com.example.map_update_search_location;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    /**
     * version in android manifest file may vary based on the gps depency installed
     * <p>
     * we need to import onMapREadtCallback
     */

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            // add = add + "\n" + obj.getCountryName();
            //  add = add + "\n" + obj.getCountryCode();
            //  add = add + "\n" + obj.getAdminArea();
            //  add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            return add;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void searchLocation(View view) {
        EditText locationSearch = findViewById(R.id.srch);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);

            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);

            LatLng loc = new LatLng(address.getLatitude(), address.getLongitude());
            map.addMarker(new MarkerOptions().position(loc).title(address.toString()));
            map.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }

    public void updateLocation(View view) {
        GPSTracker gps = new GPSTracker(this);
        Location location1 = gps.getLocation();
        if (location1 != null) {
            String adr = getAddress(gps.getLatitude(), gps.getLongitude());
            double lat = gps.getLatitude();
            double lng = gps.getLongitude();
            LatLng loc = new LatLng(lat, lng);
            map.addMarker(new MarkerOptions().position(loc).title(adr));
            map.moveCamera(CameraUpdateFactory.newLatLng(loc));
        } else {
            gps.showSettingsAlert();
        }
    }
}