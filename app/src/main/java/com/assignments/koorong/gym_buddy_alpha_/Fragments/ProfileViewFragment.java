package com.assignments.koorong.gym_buddy_alpha_.Fragments;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.assignments.koorong.gym_buddy_alpha_.MatchActivity;
import com.assignments.koorong.gym_buddy_alpha_.R;
import com.assignments.koorong.gym_buddy_alpha_.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileViewFragment extends Fragment {

    String email;
    //ProgressDialog loading;
    User selectedUser;
    View view;

    public ProfileViewFragment() {

        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        email = getArguments().getString("Email");
        //Toast.makeText(getActivity(), "Email: " + email, Toast.LENGTH_LONG).show();

//        loading = new ProgressDialog(view.getContext(), R.style.AppTheme_Dark_Dialog);
//        loading.setCancelable(false);
//        loading.setMessage("Getting Matches");
//        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new getItems().execute();

        Button setup = (Button)view.findViewById(R.id.matchButton);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "9056916760");
                smsIntent.putExtra("sms_body", "Hey I matched with you on gymbuddy");
                startActivity(smsIntent);

            }
        });

        Button setup2 = (Button)view.findViewById(R.id.denyButton);
        setup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();

            }
        });

        return view;
    }

    private class getItems extends AsyncTask<Void, Void, Void> {
//        ArrayList<User> ids;
//        User selectedUser;
//        String location;
//        Boolean error = false;
        public void onPreExecute() {
            //loading.show();
        }
    @Override
    protected Void doInBackground(Void... params) {
       // ids = new ArrayList<>();
        try {
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getActivity().getApplicationContext(),
                    "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                    Regions.US_EAST_1 // Region
            );
            AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
           // location = sm.getUserDetails().getLocation();
            DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
            selectedUser = mapper.load(User.class, email);


            //System.out.println("Result size: " + ids.size());

        }catch(Exception ex) {

        }
        return null;
    }

        @Override
        protected void onPostExecute(Void v) {
            //loading.dismiss();
            populateUI();
        }
    }

        private void populateUI(){
              String name = selectedUser.getName();
              if(name.equalsIgnoreCase("Superman"))
              {
                  TextView txtName = (TextView)view.findViewById(R.id.profileName);
                  txtName.setText("Superman");
                  TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
                  txtSummary.setText("I am the last son of the dead planet Krypton. I am looking for a gym buddy to help me train to fight Doomsday");
                  ImageView imgView = (ImageView)view.findViewById(R.id.profilePic);
                  imgView.setImageResource(R.drawable.niccage);
                  TextView txtFrequency = (TextView)view.findViewById(R.id.matchFrequency);
                  int freq =  selectedUser.getfrequency();
                  if (freq == 0)
                  {
                      txtFrequency.setText("-Works out 1-3 times a week");
                  }else if (freq == 1)
                  {
                      txtFrequency.setText("-Works out 3 times a week");
                  }else{
                      txtFrequency.setText("-Works out 5+ times a week");
                  }
                  TextView txtExp = (TextView)view.findViewById(R.id.matchExp);
                  int advan = selectedUser.getexperience();
                  if (advan == 0)
                  {
                      txtExp.setText("-Beginner");

                  }else if (advan == 1)
                  {
                      txtExp.setText("-Experienced");
                  }else{
                      txtExp.setText("-Advanced");
                  }



              }else if(name.equalsIgnoreCase("Raiden")){
                  TextView txtName = (TextView)view.findViewById(R.id.profileName);
                  txtName.setText(selectedUser.getName());
                  TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
                  txtSummary.setText("Need a training partner to help me win the Mortal Kombat Tournament");
                  ImageView imgView = (ImageView)view.findViewById(R.id.profilePic);
                  imgView.setImageResource(R.drawable.raiden);

              }else if (name.equalsIgnoreCase("Rikishi")){
                  TextView txtName = (TextView)view.findViewById(R.id.profileName);
                  txtName.setText(selectedUser.getName());
                  TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
                  txtSummary.setText("I need a training partner to help me win the world title");
                  ImageView imgView = (ImageView)view.findViewById(R.id.profilePic);
                  imgView.setImageResource(R.drawable.rikishi);
              }else if (name.equalsIgnoreCase("Brock")){
                  TextView txtName = (TextView)view.findViewById(R.id.profileName);
                  txtName.setText(selectedUser.getName());
                  TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
                  txtSummary.setText("I need a training partner to help me win the world title");
                  ImageView imgView = (ImageView)view.findViewById(R.id.profilePic);
                  imgView.setImageResource(R.drawable.brock);
              }else if (name.equalsIgnoreCase("SubZero@gmail.com")){
                  TextView txtName = (TextView)view.findViewById(R.id.profileName);
                  txtName.setText(selectedUser.getName());
                  TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
                  txtSummary.setText("I must train to defeat Scorpion and Quan Chi");
                  ImageView imgView = (ImageView)view.findViewById(R.id.profilePic);
                  imgView.setImageResource(R.drawable.subzero);
              }else{
                  TextView txtName = (TextView)view.findViewById(R.id.profileName);
                  txtName.setText(selectedUser.getName());
                  TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
                  txtSummary.setText("This user has not entered a summary yet");
                  ImageView imgView = (ImageView)view.findViewById(R.id.profilePic);
                  imgView.setImageResource(R.drawable.face);
              }
//            TextView txtName = (TextView)view.findViewById(R.id.matchName2);
//            txtName.setText(selectedUser.getName());
//            TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
//            if(selectedUser.getgoal() != null)
//            {
//                txtSummary.setText(selectedUser.getgoal());
//            }
        }

}
