package com.assignments.koorong.gym_buddy_alpha_.Fragments.ProfileSetup;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignments.koorong.gym_buddy_alpha_.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpPref extends Fragment {


    public ExpPref() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exp_pref, container, false );
        return view;    }


}
