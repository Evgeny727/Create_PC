package com.example.createpc.fragments.workshopfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.createpc.R;
import com.example.createpc.databinding.FragmentCreateBinding;
import com.example.createpc.fragments.adapters.CreateAdapter;
import com.example.createpc.fragments.dataclasses.PcCardData;
import com.example.createpc.fragments.dataclasses.StaticBuildDataTemporaryStorage;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CreateFragment extends Fragment {
    private FragmentCreateBinding fragmentCreateBinding;
    private RecyclerView mRecyclerView;
    private CreateAdapter mAdapter;
    private List<PcCardData> pcCardDataList = new ArrayList<>();

    private boolean isNeedToSave = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize default data if no saved data
        if(StaticBuildDataTemporaryStorage.isIsEmpty()) initDataset();
        else pcCardDataList = StaticBuildDataTemporaryStorage.getCardsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCreateBinding = FragmentCreateBinding.inflate(inflater, container, false);
        View view = fragmentCreateBinding.getRoot();
        Fragment fragment = this;

        mRecyclerView = fragmentCreateBinding.recyclerViewInCreatePage;
        mAdapter = new CreateAdapter(pcCardDataList, fragment);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        MaterialButton cancelButton = fragmentCreateBinding.cancelBtn;
        MaterialButton saveButton = fragmentCreateBinding.saveBtn;
        cancelButton.setOnClickListener(v -> {
            //TODO: add functionality
            navigateToStart(fragment);
        });
        saveButton.setOnClickListener(v -> {
            //TODO: add functionality
            navigateToStart(fragment);
        });
        return view;
    }

    private void navigateToStart(Fragment fragment) {
        if (!StaticBuildDataTemporaryStorage.isIsEmpty()) StaticBuildDataTemporaryStorage.clearAll();
        isNeedToSave = false;
        NavHostFragment.findNavController(fragment).navigate(R.id.action_createFragment_to_startFragment);
    }

    //Initialize data for pcPartCards
    private void initDataset() {
        String[] typeNames = getResources().getStringArray(R.array.pc_part_type_names);
        String[] specNames = getResources().getStringArray(R.array.pc_part_spec_names);
        String defaultSpecName = getString(R.string.empty_param);
        for (int i = 0; i < typeNames.length; i++) {
            int j = i*5;
            String[] specNames5 = {specNames[j], specNames[j+1], specNames[j+2], specNames[j+3], specNames[j+4]};
            String[] specValues5 = {defaultSpecName, defaultSpecName, defaultSpecName, defaultSpecName, defaultSpecName};
            pcCardDataList.add(new PcCardData(0, typeNames[i], "", "", 0, specNames5, specValues5));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Saving data in static class
        if (isNeedToSave) StaticBuildDataTemporaryStorage.setAllCards(pcCardDataList);
        pcCardDataList.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentCreateBinding = null;
    }
}