package com.example.createpc.fragments.workshopfragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.createpc.R;
import com.example.createpc.databinding.FragmentSearchAndAddBinding;
import com.example.createpc.fragments.adapters.SearchAndAddAdapter;
import com.example.createpc.fragments.dataclasses.PcCardData;

import java.util.ArrayList;
import java.util.List;

public class SearchAndAddFragment extends Fragment {
    private int partType;
    private final Fragment fragment = this;
    private FragmentSearchAndAddBinding fragmentSearchAndAddBinding;
    private RecyclerView mRecyclerView;
    private SearchAndAddAdapter mAdapter;
    private List<PcCardData> pcCardDataList = new ArrayList<>();
    private TextView noResults;
    private ImageView clearSearchText;
    private EditText textInput;

    private enum DBTableName {CPU_TABLE, GPU_TABLE, MOTHERBOARD_TABLE, PSU_TABLE, RAM_TABLE,
        CASE_TABLE, SSDM_TABLE, SSD2_TABLE, HDD_TABLE, CPU_COOLING_TABLE, CASE_COOLING_TABLE}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partType = SearchAndAddFragmentArgs.fromBundle(getArguments()).getPartTypeId();
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToCreate(fragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSearchAndAddBinding = FragmentSearchAndAddBinding.inflate(inflater, container, false);
        View view = fragmentSearchAndAddBinding.getRoot();
        noResults = fragmentSearchAndAddBinding.noResultsTextview;
        clearSearchText = fragmentSearchAndAddBinding.textInputEndIcon;
        textInput = fragmentSearchAndAddBinding.textInput;
        if (partType == -1) noResults.setVisibility(View.VISIBLE);
        clearSearchText.setOnClickListener(v -> textInput.setText(""));
        return view;
    }

    private void navigateToCreate(Fragment fragment) {
        NavHostFragment.findNavController(fragment).navigate(R.id.action_searchAndAddFragment_to_createFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO: realize search in database by component name
        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((charSequence.toString()).equals("")) clearSearchText.setVisibility(View.GONE);
                else clearSearchText.setVisibility(View.VISIBLE);
                //TODO: get from database list and set values in pcCardDataList
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        if (pcCardDataList.size() == 0) noResults.setVisibility(View.VISIBLE);
        else {
            noResults.setVisibility(View.GONE);
            mRecyclerView = fragmentSearchAndAddBinding.recyclerViewInSearchPage;
            mAdapter = new SearchAndAddAdapter(pcCardDataList, partType, fragment);
            mRecyclerView.setAdapter(mAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentSearchAndAddBinding = null;
    }
}