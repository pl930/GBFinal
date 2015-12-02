package com.assignments.koorong.gym_buddy_alpha_.Fragments.ProfileSetup;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
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
public class ProfileSetupFragment extends Fragment {

    SessionManager sm;
    public ProfileSetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile_setup, container, false);
        sm = new SessionManager(getActivity().getApplicationContext());
        Button setup = (Button) view.findViewById(R.id.btnProfileNext);
        NumberPicker np = (NumberPicker) view.findViewById(R.id.np);
        np.setMaxValue(100);
        np.setMinValue(18);
        np.setWrapSelectorWheel(false);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid();
                setValues(view);
                AgeprefFragment apFrag = new AgeprefFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.setupContainer, apFrag)
                        .commit();
            }
        });
        return view;
    }

    public void isValid()
    {

    }

    public void setValues(View view)
    {
        //EditText age = (EditText)view.findViewById(R.id.edtAge);

        NumberPicker np = (NumberPicker) view.findViewById(R.id.np);
        int age2 = np.getValue();
        boolean sex;
        RadioGroup rg = (RadioGroup)view.findViewById(R.id.maleorfemale);
        String radiovalue = ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if (radiovalue.equalsIgnoreCase("Male"))
        {
            sex = false;
        }else{
            sex = true;
        }
       // sm.getUserDetails().setAge(age2);
        //sm.getUserDetails().setGender(sex);
        sm.setUpProfile(age2, sex);
       // show(view, age2, sex);
    }

//    public void show(View view, int age, boolean sex)
//    {
//        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//                getActivity().getApplicationContext(),
//                "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
//                Regions.US_EAST_1 // Region
//        );
//        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
//        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
//        User selectedUser = mapper.load(User.class, sm.getUserDetails().getEmail());
//        selectedUser.setAge(age);
//        selectedUser.setGender(sex);
//        mapper.save(selectedUser);
//    }
}
