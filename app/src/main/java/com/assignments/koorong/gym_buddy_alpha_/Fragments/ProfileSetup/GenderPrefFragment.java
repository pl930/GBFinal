package com.assignments.koorong.gym_buddy_alpha_.Fragments.ProfileSetup;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.assignments.koorong.gym_buddy_alpha_.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenderPrefFragment extends Fragment {


    public GenderPrefFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gender_pref, container, false);

        Button setup = (Button)view.findViewById(R.id.btnGenNext);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExpPrefFragment epFrag = new ExpPrefFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.setupContainer, epFrag)
                        .commit();
            }
        });
        return view;
    }


}
