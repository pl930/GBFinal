package com.assignments.koorong.gym_buddy_alpha_.Fragments.ProfileSetup;


import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.assignments.koorong.gym_buddy_alpha_.R;
import com.assignments.koorong.gym_buddy_alpha_.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {


    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        SessionManager sm = new SessionManager(getActivity().getApplicationContext());
        String name = sm.getUserDetails().getName();
        TextView welcome = (TextView)view.findViewById(R.id.txtWelcome);
        welcome.setText("Welcome " + name);
        Button setup = (Button)view.findViewById(R.id.btnSetup);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileSetupFragment psFrag = new ProfileSetupFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.setupContainer, psFrag)
                        .commit();
            }
        });
        return view;
    }


}
