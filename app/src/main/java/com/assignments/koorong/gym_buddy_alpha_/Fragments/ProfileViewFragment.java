package com.assignments.koorong.gym_buddy_alpha_.Fragments;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.assignments.koorong.gym_buddy_alpha_.Fragments.ProfileSetup.FrequencyPrefFragment;
import com.assignments.koorong.gym_buddy_alpha_.R;
import com.assignments.koorong.gym_buddy_alpha_.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileViewFragment extends Fragment {

    String email;
    ProgressDialog loading;
    User selectedUser;
    View view;

    public ProfileViewFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        /*Get clicked on user email from intent in order to populate profile*/
        email = getArguments().getString("Email");


        /*Match Button On-Click listener*/
        Button setup = (Button) view.findViewById(R.id.matchButton);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();

            }
        });

        /*Fragments = added to backstack = pop it to go back
        * I love it*/
        Button back = (Button) view.findViewById(R.id.denyButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        new getItems().execute();

        return view;
    }

    /*Called on match button click. Sends sms via either Hangouts or sms app.*/
    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getActivity().getApplicationContext()); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + Uri.encode("9056916760")));
            sendIntent.putExtra("sms_body", "Hey I just matched with you on GymBuddy");


            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        } else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", "phoneNumber");
            smsIntent.putExtra("sms_body", "message");
            startActivity(smsIntent);
        }
    }

    /*Database call*/
    private class getItems extends AsyncTask<Void, Void, Void> {
        public void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Database call
            try {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getActivity().getApplicationContext(),
                        "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                        Regions.US_EAST_1 // Region
                );
                AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
                DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
                selectedUser = mapper.load(User.class, email);



            } catch (Exception ex) {

            }
            return null;
        }

        /*Call populateUI()*/
        @Override
        protected void onPostExecute(Void v) {
            //loading.dismiss();
            populateUI();
        }
    }

    /*Populate UI with info from selected user. Hardcoded summary for now.*/
    private void populateUI() {
        String name = selectedUser.getName();
        int freq = selectedUser.getfrequency();
        int advan = selectedUser.getexperience();

        if (name.equalsIgnoreCase("Superman")) {
            TextView txtSummary = (TextView) view.findViewById(R.id.matchSummary2);
            txtSummary.setText("I am the last son of the dead planet Krypton. I am looking for a gym buddy to help me train to fight Doomsday");
            ImageView imgView = (ImageView) view.findViewById(R.id.profilePic);
            imgView.setImageResource(R.drawable.niccage);
            TextView txtFrequency = (TextView) view.findViewById(R.id.matchFrequency);
            setUserInfo(freq,advan,name);


        } else if (name.equalsIgnoreCase("Raiden")) {
            setUserInfo(freq, advan, name);
            TextView txtSummary = (TextView) view.findViewById(R.id.matchSummary2);
            txtSummary.setText("Need a training partner to help me win the Mortal Kombat Tournament");
            ImageView imgView = (ImageView) view.findViewById(R.id.profilePic);
            imgView.setImageResource(R.drawable.raiden);

        } else if (name.equalsIgnoreCase("Rikishi")) {
            setUserInfo(freq, advan, name);
            TextView txtName = (TextView) view.findViewById(R.id.profileName);
            txtName.setText(selectedUser.getName());
            TextView txtSummary = (TextView) view.findViewById(R.id.matchSummary2);
            txtSummary.setText("I need a training partner to help me win the world title");
            ImageView imgView = (ImageView) view.findViewById(R.id.profilePic);
            imgView.setImageResource(R.drawable.rikishi);

        } else if (name.equalsIgnoreCase("Brock")) {
            setUserInfo(freq, advan, name);
            TextView txtName = (TextView) view.findViewById(R.id.profileName);
            txtName.setText(selectedUser.getName());
            TextView txtSummary = (TextView) view.findViewById(R.id.matchSummary2);
            txtSummary.setText("I need a training partner to help me win the world title");
            ImageView imgView = (ImageView) view.findViewById(R.id.profilePic);
            imgView.setImageResource(R.drawable.brock);
        } else if (name.equalsIgnoreCase("SubZero@gmail.com")) {
            setUserInfo(freq, advan, name);
            TextView txtName = (TextView) view.findViewById(R.id.profileName);
            txtName.setText(selectedUser.getName());
            TextView txtSummary = (TextView) view.findViewById(R.id.matchSummary2);
            txtSummary.setText("I must train to defeat Scorpion and Quan Chi");
            ImageView imgView = (ImageView) view.findViewById(R.id.profilePic);
            imgView.setImageResource(R.drawable.subzero);
        } else {
            setUserInfo(freq, advan, name);
            TextView txtSummary = (TextView) view.findViewById(R.id.matchSummary2);
            txtSummary.setText("This user has not entered a summary yet");
            ImageView imgView = (ImageView) view.findViewById(R.id.profilePic);
            imgView.setImageResource(R.drawable.face);
        }
    }

    /*Set user info. Called above*/
    public void setUserInfo(int freq, int advan, String name){
        TextView txtName = (TextView)view.findViewById(R.id.profileName);
        txtName.setText(selectedUser.getName());
        TextView txtFrequency = (TextView) view.findViewById(R.id.matchFrequency);
        if (freq == 0) {
            txtFrequency.setText("-Works out 1-3 times a week");
        } else if (freq == 1) {
            txtFrequency.setText("-Works out 3 times a week");
        } else {
            txtFrequency.setText("-Works out 5+ times a week");
        }
        TextView txtExp = (TextView) view.findViewById(R.id.matchExp);
        if (advan == 0) {
            txtExp.setText("-Beginner");

        } else if (advan == 1) {
            txtExp.setText("-Experienced");
        } else {
            txtExp.setText("-Advanced");
        }
    }

}
