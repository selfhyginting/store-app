package com.example.appsselfhy.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appsselfhy.R;
import com.example.appsselfhy.ReviewActivity;
import com.example.appsselfhy.adapter.GridAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFragment extends Fragment {


    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewActivity.class);
                startActivity(intent);
            }
        }); // fab click
        mRecycleView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(),2);

        mRecycleView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter();
        mRecycleView.setAdapter(mAdapter);



        return view;
    }
}