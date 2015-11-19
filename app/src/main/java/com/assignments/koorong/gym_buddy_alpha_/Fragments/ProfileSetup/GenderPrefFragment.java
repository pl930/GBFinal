package com.assignments.koorong.gym_buddy_alpha_.Fragments.ProfileSetup;


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
import com.assignments.koorong.gym_buddy_alpha_.R;
import com.assignments.koorong.gym_buddy_alpha_.SessionManager;
import com.assignments.koorong.gym_buddy_alpha_.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenderPrefFragment extends Fragment {

    SessionManager sm;
    public GenderPrefFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_gender_pref, container, false);
        sm = new SessionManager(getActivity().getApplicationContext());
        Button setup = (Button)view.findViewById(R.id.btnGenNext);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues(view);
                ExpPrefFragment epFrag = new ExpPrefFragment();
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
        int selected =2;
        RadioGroup rg = (RadioGroup)view.findViewById(R.id.radioGenderPref);
        String radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("Male"))
        {
            selected = 0;
        }
        if (radiovalue.equalsIgnoreCase("Female"))
        {
            selected = 1;
        }
        if (radiovalue.equalsIgnoreCase("No Preference"))
        {
            selected = 2;
        }


        show(view, selected);
    }

    public void show(View view, int selected)
    {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getActivity().getApplicationContext(),
                "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
        User selectedUser = mapper.load(User.class, sm.getUserDetails().getEmail());
        selectedUser.setgenderPref(selected);
        mapper.save(selectedUser);
    }
}
