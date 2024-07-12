package com.example.firstproject;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.firstproject.Fragments.ListFragment;
import com.example.firstproject.Fragments.MapFragment;
import com.example.firstproject.Interface.Callback_ListItemClicked;

import java.util.Map;

public class ScoreActivity extends AppCompatActivity {
    private FrameLayout main_FRAME_list;
    private FrameLayout main_FRAME_map;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        findViews();
        initViews();
    }

    private void initViews() {
        listFragment = new ListFragment();
        listFragment.setCallbackListItemClicked(new Callback_ListItemClicked(){
            @Override
            public void listItemClicked(double lat, double lon){
                mapFragment.zoom(lat, lon);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();

        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }

    private void findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list);
        main_FRAME_map = findViewById(R.id.main_FRAME_map);
    }


}