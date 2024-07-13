package com.example.firstproject.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstproject.Interface.Callback_ListItemClicked;

import com.example.firstproject.Models.HighScoreRepository;
import com.example.firstproject.Models.Score;
import com.example.firstproject.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private MaterialTextView list_LBL_title;
    private MaterialButton[] score_buttons_arr;

    HighScoreRepository highScoreRepository;
    ArrayList<Score> scores_list;
    Callback_ListItemClicked callbackListItemClicked;

    public ListFragment() {
        // Required empty public constructor
    }

    public void setCallbackListItemClicked(Callback_ListItemClicked callbackListItemClicked) {
        this.callbackListItemClicked = callbackListItemClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        highScoreRepository = HighScoreRepository.getInstance();
        scores_list = highScoreRepository.getScores();
        findViews(v);
        initViews();
        return v;
    }

    private void initViews() {
        for (int i = 0; i < score_buttons_arr.length; i++) {
            if(i < scores_list.size()){
                String score = String.format("%-20s     %d", scores_list.get(i).getName(), scores_list.get(i).getScore());
                score_buttons_arr[i].setText(score);
            }else{
                score_buttons_arr[i].setVisibility(View.INVISIBLE);
            }
        }
        for (int i = 0; i < scores_list.size(); i++) {
            double lat = scores_list.get(i).getLat();
            double lon = scores_list.get(i).getLon();
            score_buttons_arr[i].setOnClickListener(v -> itemClicked(lat, lon));
        }
    }

    private void itemClicked(double lat, double lon) {
        if (callbackListItemClicked != null)
            callbackListItemClicked.listItemClicked(lat, lon);
    }

    private void findViews(View v) {
        list_LBL_title = v.findViewById(R.id.list_LBL_title);
        score_buttons_arr = new MaterialButton[]{
                v.findViewById(R.id.list_LBL_score1),
                v.findViewById(R.id.list_LBL_score2),
                v.findViewById(R.id.list_LBL_score3),
                v.findViewById(R.id.list_LBL_score4),
                v.findViewById(R.id.list_LBL_score5),
                v.findViewById(R.id.list_LBL_score6),
                v.findViewById(R.id.list_LBL_score7),
                v.findViewById(R.id.list_LBL_score8),
                v.findViewById(R.id.list_LBL_score9),
                v.findViewById(R.id.list_LBL_score10)

        };
    }
}