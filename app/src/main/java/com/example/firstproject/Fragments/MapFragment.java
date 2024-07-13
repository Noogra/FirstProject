package com.example.firstproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.firstproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textview.MaterialTextView;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap myMap;
    private SupportMapFragment mapFragment;
    private double lat;
    private double lon;
    private float zoomLevel = 10.0f;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(v);
        initViews(v);

        return v;
    }

    private void initViews(View v) {
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void findViews(View v) {
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_LBL_map);
    }

    public void zoom(double lat, double lon) {
       this.lat = lat;
       this.lon = lon;
       onMapReady(myMap);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        LatLng location = new LatLng(lat, lon);
        myMap.addMarker(new MarkerOptions().position(location));
        //myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel), 2000, null);
    }
}