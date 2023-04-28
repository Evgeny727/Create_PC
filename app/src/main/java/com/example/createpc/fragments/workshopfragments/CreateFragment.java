package com.example.createpc.fragments.workshopfragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import com.example.createpc.fragments.dialogs.SaveBuildDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CreateFragment extends Fragment {
    private FragmentCreateBinding fragmentCreateBinding;
    private RecyclerView mRecyclerView;
    private CreateAdapter mAdapter;
    private List<PcCardData> pcCardDataList = new ArrayList<>();
    private int build_id = -1;
    private final Fragment fragment = this;
    private String build_name = "";
    private boolean isNeedToSave = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("NameKey", this, (requestKey, bundle) -> {
            build_name = bundle.getString("build_name");
            //TODO: realize saving build into database
            navigateToStart(fragment);
        });
        build_id = CreateFragmentArgs.fromBundle(getArguments()).getBuildId();
        if (build_id > 0) {
            OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    navigateToStart(fragment);
                }
            };
            requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
            pcCardDataList = StaticBuildDataTemporaryStorage.getCardsList();
        }
        else {
            if(StaticBuildDataTemporaryStorage.isIsEmpty()) initDataset(); //Initialize default data if no saved data
            else pcCardDataList = StaticBuildDataTemporaryStorage.getCardsList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCreateBinding = FragmentCreateBinding.inflate(inflater, container, false);
        View view = fragmentCreateBinding.getRoot();

        mRecyclerView = fragmentCreateBinding.recyclerViewInCreatePage;
        mAdapter = new CreateAdapter(pcCardDataList, fragment);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        MaterialButton cancelButton = fragmentCreateBinding.cancelBtn;
        MaterialButton saveButton = fragmentCreateBinding.saveBtn;
        cancelButton.setOnClickListener(v -> {
            navigateToStart(fragment);
        });
        saveButton.setOnClickListener(v -> {
            SaveBuildDialogFragment dialogFragment = new SaveBuildDialogFragment();
            Bundle args = new Bundle();
            args.putInt("id", build_id);
            dialogFragment.setArguments(args);
            dialogFragment.show(fragment.getParentFragmentManager(), "save");
        });
        return view;
    }

    private void navigateToStart(Fragment fragment) {
        if (!StaticBuildDataTemporaryStorage.isIsEmpty()) StaticBuildDataTemporaryStorage.clearAll();
        isNeedToSave = false;
        if (build_id > 0) {
            NavHostFragment.findNavController(fragment).navigate(R.id.action_createFragment_to_buildsFragment);
        }
        else {
            NavHostFragment.findNavController(fragment).navigate(R.id.action_createFragment_to_startFragment);
        }
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