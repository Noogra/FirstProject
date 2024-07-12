package com.example.firstproject.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstproject.Interface.Callback_ListItemClicked;

import com.example.firstproject.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class ListFragment extends Fragment {

    private MaterialTextView list_LBL_title;
    private MaterialButton list_LBL_send;

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
        findViews(v);
        initViews();
        return v;
    }

    private void initViews() {
        list_LBL_send.setOnClickListener(v -> itemClicked(32.1129923, 34.8182147));
    }

    private void itemClicked(double lat, double lon) {
        if (callbackListItemClicked != null)
            callbackListItemClicked.listItemClicked(lat, lon);
    }

    private void findViews(View v) {
        list_LBL_title = v.findViewById(R.id.list_LBL_title);
        list_LBL_send = v.findViewById(R.id.list_LBL_send);

    }
}