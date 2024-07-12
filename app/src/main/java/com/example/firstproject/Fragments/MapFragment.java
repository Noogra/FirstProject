package com.example.firstproject.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.firstproject.R;
import com.google.android.material.textview.MaterialTextView;


public class MapFragment extends Fragment {

    private MaterialTextView map_LBL_map;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(v);

        return v;
    }

    private void findViews(View v) {
        map_LBL_map = v.findViewById(R.id.map_LBL_map);
    }

    public void zoom(double lat, double lon) {
        map_LBL_map.setText(lat + "\n" + lon);
    }
}