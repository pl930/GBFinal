package com.assignments.koorong.gym_buddy_alpha_.Fragments.ProfileSetup;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.assignments.koorong.gym_buddy_alpha_.R;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgeprefFragment extends Fragment {


    public AgeprefFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agepref, container, false);
        Button setup = (Button)view.findViewById(R.id.btnAgePrefNext);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GenderPrefFragment gpFrag = new GenderPrefFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.setupContainer, gpFrag)
                        .commit();
            }
        });
        return view;
    }


}
