package com.painlessshopping.mohamed.findit;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final float DEFAULTZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

//    private void gotoLocation(double lat, double lng, float zoom) {
//        LatLng ll = new LatLng(lat, lng);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
//        mMap.moveCamera(update);
//}
    public void fetchLoc(View v){

        EditText et = (EditText) findViewById(R.id.TFaddress);
        String loc = et.getText().toString();

        if(loc !=null && !loc.equals("")) {

            Geocoder coder = new Geocoder(MapsActivity.this);
            List<Address> list = null;

            try {
                list = coder.getFromLocationName(loc, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address a = list.get(0);

            String locality = a.getLocality();

            LatLng latlng = new LatLng(a.getLatitude(), a.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latlng).title("from geocoder"));
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, DEFAULTZOOM);
            mMap.animateCamera(update);
        }


//        gotoLocation(lat, lng, DEFAULTZOOM);
        }




    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;

//            LatLng sydney = new LatLng(-34, 151);
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
