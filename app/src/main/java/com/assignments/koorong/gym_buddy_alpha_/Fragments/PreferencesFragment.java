package com.assignments.koorong.gym_buddy_alpha_.Fragments;


import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextThemeWrapper;
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
    int genPref;
    int freq;
    int exp;
    int agePref;

    SessionManager sm;
    public PreferencesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Dark_Dialog);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        final View view = localInflater.inflate(R.layout.fragment_preferences, container, false);
        sm = new SessionManager(getActivity().getApplicationContext());
        Button setup = (Button)view.findViewById(R.id.btnExpNext2);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues(view);
                new updatePrefs().execute();
                MatchUserFragment epFrag = new MatchUserFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.content_frame, epFrag)
                        .commit();
            }
        });
        return view;
    }

    public void setValues(View view)
    {
        RadioGroup rg = (RadioGroup)view.findViewById(R.id.radioGenderPrefSet);
        String radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("Male"))
        {
            genPref = 0;
        }
        if (radiovalue.equalsIgnoreCase("Female"))
        {
            genPref = 1;
        }
        if (radiovalue.equalsIgnoreCase("No Preference"))
        {
            genPref = 2;
        }

        int frequency =0;
        rg = (RadioGroup)view.findViewById(R.id.radioFreqSet);
        radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("1 to 3"))
        {
            freq = 0;
        }
        if (radiovalue.equalsIgnoreCase("3 to 5"))
        {
            freq = 1;
        }
        if (radiovalue.equalsIgnoreCase("5+"))
        {
            freq = 2;
        }

        rg = (RadioGroup)view.findViewById(R.id.expPrefRSet);
        radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("Beginner"))
        {
            exp = 0;
        }
        if (radiovalue.equalsIgnoreCase("Intermediate"))
        {
            exp = 1;
        }
        if (radiovalue.equalsIgnoreCase("Advanced"))
        {
            exp = 2;
        }

        rg = (RadioGroup)view.findViewById(R.id.agePrefGrSet);
        radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("18 - 24"))
        {
            agePref = 0;
        }
        if (radiovalue.equalsIgnoreCase("25 - 30"))
        {
            agePref = 1;
        }
        if (radiovalue.equalsIgnoreCase("31 - 38"))
        {
            agePref = 2;
        }
        if (radiovalue.equalsIgnoreCase("No Preference"))
        {
            agePref = 3;
        }
    }


    private class updatePrefs extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getActivity().getApplicationContext(),
                    "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                    Regions.US_EAST_1 // Region
            );
            AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
            DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
            User selectedUser = mapper.load(User.class, sm.getUserDetails().getEmail());
            selectedUser.setgenderPref(genPref);
            selectedUser.setfrequency(freq);
            selectedUser.setexperience(exp);
            selectedUser.setAgePref(agePref);
            mapper.save(selectedUser);

            return null;
        }
    }
}
