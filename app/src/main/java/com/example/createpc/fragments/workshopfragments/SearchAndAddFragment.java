package com.example.createpc.fragments.workshopfragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.createpc.R;
import com.example.createpc.databinding.FragmentSearchAndAddBinding;
import com.example.createpc.fragments.adapters.SearchAndAddAdapter;
import com.example.createpc.fragments.dataclasses.DatabaseHelper;
import com.example.createpc.fragments.dataclasses.PcCardData;

import java.util.ArrayList;
import java.util.List;

public class SearchAndAddFragment extends Fragment {
    private int partType;
    private final Fragment fragment = this;
    private FragmentSearchAndAddBinding fragmentSearchAndAddBinding;
    private RecyclerView mRecyclerView;
    private SearchAndAddAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<PcCardData> pcCardDataList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private TextView noResults;
    private ImageView clearSearchText;
    private EditText textInput;

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
        databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
        databaseHelper.create_db();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSearchAndAddBinding = FragmentSearchAndAddBinding.inflate(inflater, container, false);
        View view = fragmentSearchAndAddBinding.getRoot();
        mRecyclerView = fragmentSearchAndAddBinding.recyclerViewInSearchPage;
        noResults = fragmentSearchAndAddBinding.noResultsTextview;
        clearSearchText = fragmentSearchAndAddBinding.textInputEndIcon;
        textInput = fragmentSearchAndAddBinding.textInput;
        if (partType == -1) noResults.setVisibility(View.VISIBLE);
        clearSearchText.setOnClickListener(v -> textInput.setText(""));

        db = databaseHelper.open();
        cursor = db.rawQuery("select * from " + DatabaseHelper.TABLEs[partType], null);
        setPcCardDataList(cursor);
        if (!pcCardDataList.isEmpty()) {
            noResults.setVisibility(View.GONE);
            mAdapter = new SearchAndAddAdapter(pcCardDataList, partType, fragment);
            mRecyclerView.setAdapter(mAdapter);
            layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        return view;
    }

    private void navigateToCreate(Fragment fragment) {
        NavHostFragment.findNavController(fragment).navigate(R.id.action_searchAndAddFragment_to_createFragment);
    }

    private void setPcCardDataList(Cursor cursor) {
        if (cursor.moveToFirst()) {
            String[] typeNames = getResources().getStringArray(R.array.pc_part_type_names);
            String[] specNames = getResources().getStringArray(R.array.pc_part_spec_names);
            int j = partType*5;
            String[] specNames5 = {specNames[j], specNames[j+1], specNames[j+2], specNames[j+3], specNames[j+4]};
            for (int i = 0; i < cursor.getCount(); i++) {
                String[] specValues = {cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)};
                pcCardDataList.add(new PcCardData(cursor.getInt(0), typeNames[partType], cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), specNames5, specValues));
                if (!cursor.moveToNext()) break;
            }
        }
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
        db.close();
        cursor.close();
    }
}