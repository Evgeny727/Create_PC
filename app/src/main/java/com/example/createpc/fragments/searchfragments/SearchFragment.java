package com.example.createpc.fragments.searchfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.createpc.databinding.FragmentSearchBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FragmentSearchBinding fragmentSearchBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = fragmentSearchBinding.getRoot();
//        ConstraintLayout constraintLayout = fragmentSearchBinding.categoryLayout;
//        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
//            constraintLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //TODO: realise navigate to part_search_list in particular category
//                }
//            });
//        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentSearchBinding = null;
    }
}