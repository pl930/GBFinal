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

        // Inflate the layout for this fragment
        Bundle info = this.getArguments();
        String nameReceived = info.getString("test");

        TextView name = (TextView) view.findViewById(R.id.matchName);
        Log.i("test","Name: " + nameReceived);
        name.setText(nameReceived);
      //  name.setText(nameReceived);

        return inflater.inflate(R.layout.fragment_profile_view, container, false);
    }

}
