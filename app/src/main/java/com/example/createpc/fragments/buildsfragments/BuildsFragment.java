package com.example.createpc.fragments.buildsfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.createpc.databinding.FragmentBuildsBinding;
import com.example.createpc.fragments.adapters.BuildsAdapter;
import com.example.createpc.fragments.dataclasses.PcCardData;

import java.util.ArrayList;
import java.util.List;

public class BuildsFragment extends Fragment {
    private final Fragment fragment = this;
    private FragmentBuildsBinding fragmentBuildBinding;
    private RecyclerView mRecyclerView;
    private BuildsAdapter mAdapter;
    private List<List<PcCardData>> pcCardDataList = new ArrayList<>();

    private String[] buildsName;
    private TextView noBuilds;
    private enum DBTableName {CPU_TABLE, GPU_TABLE, MOTHERBOARD_TABLE, PSU_TABLE, RAM_TABLE,
        CASE_TABLE, SSDM_TABLE, SSD2_TABLE, HDD_TABLE, CPU_COOLING_TABLE, CASE_COOLING_TABLE}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBuildBinding = FragmentBuildsBinding.inflate(inflater, container, false);
        View view = fragmentBuildBinding.getRoot();
        mRecyclerView = fragmentBuildBinding.recyclerViewInBuildPage;
        noBuilds = fragmentBuildBinding.noBuildsTextview;

        //TODO: realize fetching data from database
        if (pcCardDataList.isEmpty()) noBuilds.setVisibility(View.VISIBLE);
        else {
            noBuilds.setVisibility(View.GONE);
            mAdapter = new BuildsAdapter(pcCardDataList, buildsName, fragment);
            mRecyclerView.setAdapter(mAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentBuildBinding = null;
    }
}