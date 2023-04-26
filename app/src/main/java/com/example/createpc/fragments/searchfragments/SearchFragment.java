package com.example.createpc.fragments.searchfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.createpc.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentSearchBinding fragmentSearchBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = fragmentSearchBinding.getRoot();
        Fragment fragment = this;
        ConstraintLayout constraintLayout = fragmentSearchBinding.categoryLayout;
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            final int position = i;
            constraintLayout.getChildAt(i).setOnClickListener(v ->
                    NavHostFragment.findNavController(fragment).navigate(SearchFragmentDirections.actionSearchFragmentToSearchComponentsFragment().setTypeId(position)));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentSearchBinding = null;
    }
}