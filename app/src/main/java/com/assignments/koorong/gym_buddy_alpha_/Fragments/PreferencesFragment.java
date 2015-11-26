package com.assignments.koorong.gym_buddy_alpha_.Fragments;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.assignments.koorong.gym_buddy_alpha_.Fragments.ProfileSetup.ExpPrefFragment;
import com.assignments.koorong.gym_buddy_alpha_.R;
import com.assignments.koorong.gym_buddy_alpha_.SessionManager;
import com.assignments.koorong.gym_buddy_alpha_.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferencesFragment extends Fragment {

    SessionManager sm;
    public PreferencesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_preferences, container, false);
        sm = new SessionManager(getActivity().getApplicationContext());
        Button setup = (Button)view.findViewById(R.id.btnExpNext2);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues(view);
                MatchUserFragment epFrag = new MatchUserFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.setupContainer, epFrag)
                        .commit();
            }
        });
        return view;
    }

    public void setValues(View view)
    {
        int genderPref =0;
        RadioGroup rg = (RadioGroup)view.findViewById(R.id.radioGenderPrefSet);
        String radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("Male"))
        {
            genderPref = 0;
        }
        if (radiovalue.equalsIgnoreCase("Female"))
        {
            genderPref = 1;
        }
        if (radiovalue.equalsIgnoreCase("No Preference"))
        {
            genderPref = 2;
        }

        int frequency =0;
        rg = (RadioGroup)view.findViewById(R.id.radioFreqSet);
        radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("1 to 3"))
        {
            frequency = 0;
        }
        if (radiovalue.equalsIgnoreCase("3 to 5"))
        {
            frequency = 1;
        }
        if (radiovalue.equalsIgnoreCase("5+"))
        {
            frequency = 2;
        }

        int experience =0;
        rg = (RadioGroup)view.findViewById(R.id.expPrefRSet);
        radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("Beginner"))
        {
            experience = 0;
        }
        if (radiovalue.equalsIgnoreCase("Intermediate"))
        {
            experience = 1;
        }
        if (radiovalue.equalsIgnoreCase("Advanced"))
        {
            experience = 2;
        }

        int agepref =0;
        rg = (RadioGroup)view.findViewById(R.id.agePrefGrSet);
        radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("18 - 24"))
        {
            agepref = 0;
        }
        if (radiovalue.equalsIgnoreCase("25 - 30"))
        {
            agepref = 1;
        }
        if (radiovalue.equalsIgnoreCase("31 - 38"))
        {
            agepref = 2;
        }
        if (radiovalue.equalsIgnoreCase("No Preference"))
        {
            agepref = 3;
        }


        show(view, genderPref, frequency, experience, agepref);
    }



    public void show(View view, int genderPref, int frequency, int experience, int agepref)
    {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getActivity().getApplicationContext(),
                "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
        User selectedUser = mapper.load(User.class, sm.getUserDetails().getEmail());
        selectedUser.setgenderPref(genderPref);
        selectedUser.setfrequency(frequency);
        selectedUser.setexperience(experience);
        selectedUser.setAgePref(agepref);
        mapper.save(selectedUser);
    }
}
