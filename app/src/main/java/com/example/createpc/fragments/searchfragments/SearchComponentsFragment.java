package com.example.createpc.fragments.searchfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import com.example.createpc.databinding.FragmentSearchComponentsBinding;
import com.example.createpc.fragments.adapters.SearchComponentsAdapter;
import com.example.createpc.fragments.dataclasses.PcCardData;

import java.util.ArrayList;
import java.util.List;

public class SearchComponentsFragment extends Fragment {
    private int partType;
    private final Fragment fragment = this;
    private FragmentSearchComponentsBinding fragmentSearchComponentsBinding;
    private RecyclerView mRecyclerView;
    private SearchComponentsAdapter mAdapter;
    private List<PcCardData> pcCardDataList = new ArrayList<>();
    private TextView noResults;
    private ImageView clearSearchText;
    private EditText textInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partType = SearchComponentsFragmentArgs.fromBundle(getArguments()).getPartTypeId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSearchComponentsBinding = FragmentSearchComponentsBinding.inflate(inflater, container, false);
        View view = fragmentSearchComponentsBinding.getRoot();
        noResults = fragmentSearchComponentsBinding.noResultsTextview;
        clearSearchText = fragmentSearchComponentsBinding.textInputEndIcon;
        textInput = fragmentSearchComponentsBinding.textInput;
        if (partType == -1) noResults.setVisibility(View.VISIBLE);
        clearSearchText.setOnClickListener(v -> textInput.setText(""));
        return view;
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
            mRecyclerView = fragmentSearchComponentsBinding.recyclerViewInSearchPage;
            mAdapter = new SearchComponentsAdapter(pcCardDataList, partType, fragment);
            mRecyclerView.setAdapter(mAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentSearchComponentsBinding = null;
    }
}