package com.example.createpc.fragments.buildsfragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.createpc.R;
import com.example.createpc.databinding.FragmentBuildComponentsBinding;
import com.example.createpc.fragments.adapters.BuildComponentsAdapter;
import com.example.createpc.fragments.dataclasses.DatabaseBuildsHelper;

public class BuildComponentsFragment extends Fragment {
    private final Fragment fragment = this;
    private int build_id;
    private String build_name;
    private String price;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        build_name = BuildComponentsFragmentArgs.fromBundle(requireArguments()).getName();
        price = BuildComponentsFragmentArgs.fromBundle(requireArguments()).getPrice();
        build_id = BuildComponentsFragmentArgs.fromBundle(requireArguments()).getId();
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToBuild(fragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
        @SuppressWarnings("resource") DatabaseBuildsHelper helper = new DatabaseBuildsHelper(requireActivity().getApplicationContext());
        db = helper.getWritableDatabase();
    }

    FragmentBuildComponentsBinding fragmentBuildComponentsBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBuildComponentsBinding = FragmentBuildComponentsBinding.inflate(inflater, container, false);
        View view = fragmentBuildComponentsBinding.getRoot();
        RecyclerView mRecyclerView = fragmentBuildComponentsBinding.recyclerViewInShowPage;
        ImageButton imageButton = fragmentBuildComponentsBinding.backBtn;
        imageButton.setOnClickListener(v -> navigateToBuild(fragment));
        TextView price_view = fragmentBuildComponentsBinding.price;
        price_view.setText(price);
        TextView name_view = fragmentBuildComponentsBinding.name;
        name_view.setText(build_name);

        cursor = db.rawQuery("select * from " + DatabaseBuildsHelper.TABLE + " where _id=" + build_id, null);
        BuildComponentsAdapter mAdapter = new BuildComponentsAdapter(cursor, fragment);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void navigateToBuild(Fragment fragment) {
        NavHostFragment.findNavController(fragment).navigate(R.id.action_buildComponentsFragment_to_buildsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentBuildComponentsBinding = null;
        db.close();
        cursor.close();
    }
}