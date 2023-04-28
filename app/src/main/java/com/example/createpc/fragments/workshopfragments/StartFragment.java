package com.example.createpc.fragments.workshopfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.createpc.R;
import com.example.createpc.databinding.FragmentStartBinding;
import com.google.android.material.button.MaterialButton;

public class StartFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentStartBinding fragmentStartBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStartBinding = FragmentStartBinding.inflate(inflater, container, false);
        View view = fragmentStartBinding.getRoot();
        MaterialButton materialButton = fragmentStartBinding.createBtn;
        Fragment fragment = this;
        materialButton.setOnClickListener(view1 -> {
            StartFragmentDirections.ActionStartFragmentToCreateFragment action = StartFragmentDirections.actionStartFragmentToCreateFragment();
            action.setBuildId(-1);
            action.setBuildName("");
            CreateFragment.isNeedToSaveId = false;
            NavHostFragment.findNavController(fragment).navigate(action);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentStartBinding = null;
    }

}