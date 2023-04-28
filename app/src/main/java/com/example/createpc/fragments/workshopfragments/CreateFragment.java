package com.example.createpc.fragments.workshopfragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.createpc.R;
import com.example.createpc.databinding.FragmentCreateBinding;
import com.example.createpc.fragments.adapters.CreateAdapter;
import com.example.createpc.fragments.dataclasses.DatabaseBuildsHelper;
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
    private static int build_id = -1;
    private final Fragment fragment = this;
    private static String build_name = "";
    private boolean isNeedToSave = true;
    public static boolean isNeedToSaveId = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("NameKey", this, (requestKey, bundle) -> {
            build_name = bundle.getString("build_name");
            DatabaseBuildsHelper databaseBuildsHelper = new DatabaseBuildsHelper(getActivity().getApplicationContext());
            SQLiteDatabase db = databaseBuildsHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DatabaseBuildsHelper.COLUMN_NAME, build_name);
            cv.put(DatabaseBuildsHelper.COLUMN_CPU, pcCardDataList.get(0).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_GPU, pcCardDataList.get(1).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_MOTHERBOARD, pcCardDataList.get(2).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_PSU, pcCardDataList.get(3).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_RAM, pcCardDataList.get(4).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_COOLER, pcCardDataList.get(5).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_CASE, pcCardDataList.get(6).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_SSDM, pcCardDataList.get(7).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_SSD2, pcCardDataList.get(8).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_HDD, pcCardDataList.get(9).getId());
            cv.put(DatabaseBuildsHelper.COLUMN_FAN, pcCardDataList.get(10).getId());
            if (build_id > 0) {
                db.update(DatabaseBuildsHelper.TABLE, cv, DatabaseBuildsHelper.COLUMN_ID + "=" + build_id, null);
            }
            else {
                db.insert(DatabaseBuildsHelper.TABLE, null, cv);
            }
            db.close();
            navigateToStart(fragment);
        });
        if (!isNeedToSaveId) {
            build_id = CreateFragmentArgs.fromBundle(getArguments()).getBuildId();
            build_name = CreateFragmentArgs.fromBundle(getArguments()).getBuildName();
        }
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
        cancelButton.setOnClickListener(v -> navigateToStart(fragment));
        saveButton.setOnClickListener(v -> {
            if (!StaticBuildDataTemporaryStorage.isIsEmpty()) {
                SaveBuildDialogFragment dialogFragment = new SaveBuildDialogFragment();
                Bundle args = new Bundle();
                args.putInt("id", build_id);
                if (!build_name.equals("")) args.putString("name", build_name);
                dialogFragment.setArguments(args);
                dialogFragment.show(fragment.getParentFragmentManager(), "save");
            }
            else navigateToStart(fragment);
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