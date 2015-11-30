package com.assignments.koorong.gym_buddy_alpha_.Fragments;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.assignments.koorong.gym_buddy_alpha_.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileViewFragment extends Fragment {


    public ProfileViewFragment() {

        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        String email = getArguments().getString("Email");
        Toast.makeText(getActivity(), "Email: " + email, Toast.LENGTH_LONG).show();

        return view;
    }



}
