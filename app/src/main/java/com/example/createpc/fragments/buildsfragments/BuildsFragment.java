package com.example.createpc.fragments.buildsfragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.createpc.databinding.FragmentBuildsBinding;
import com.example.createpc.fragments.adapters.BuildsAdapter;
import com.example.createpc.fragments.dataclasses.DatabaseBuildsHelper;
import com.example.createpc.fragments.dataclasses.DatabaseHelper;
import com.example.createpc.fragments.dataclasses.PcCardData;

import java.util.ArrayList;
import java.util.List;

public class BuildsFragment extends Fragment {
    private final Fragment fragment = this;
    private FragmentBuildsBinding fragmentBuildBinding;
    private RecyclerView mRecyclerView;
    private BuildsAdapter mAdapter;
    private TextView noBuilds;
    private DatabaseBuildsHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseBuildsHelper(getActivity().getApplicationContext());
        db = helper.getWritableDatabase();
        getParentFragmentManager().setFragmentResultListener("BuildKey", this, (requestKey, bundle) -> {
            int build_id = bundle.getInt("id");
            db.delete(DatabaseBuildsHelper.TABLE, DatabaseBuildsHelper.COLUMN_ID + "=" + build_id, null);
            db.close();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBuildBinding = FragmentBuildsBinding.inflate(inflater, container, false);
        View view = fragmentBuildBinding.getRoot();
        mRecyclerView = fragmentBuildBinding.recyclerViewInBuildPage;
        noBuilds = fragmentBuildBinding.noBuildsTextview;

        //TODO: realize fetching data from database
        cursor = db.rawQuery("select * from " + DatabaseBuildsHelper.TABLE, null);
        if (cursor.getCount() > 0) {
            noBuilds.setVisibility(View.GONE);
            mAdapter = new BuildsAdapter(cursor, fragment);
            mRecyclerView.setAdapter(mAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        else noBuilds.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentBuildBinding = null;
        try {
            mAdapter.closeDB();
        }
        catch (Exception e) {
            Log.d("TAGAdapter", "Adapter doesn't exist");
        }
        db.close();
        cursor.close();
    }
}