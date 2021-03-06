package com.assignments.koorong.gym_buddy_alpha_.Fragments;


import android.app.Activity;
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

import java.util.ArrayList;

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //At least KitKat
                {
                    Activity activity = getActivity();
                    String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(activity); //Need to change the build to API 19

                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + Uri.encode("9056916760")));
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey I matched with you on gymbuddy");

                    if (defaultSmsPackageName != null)//Can be null in case that there is no default, then the user would be able to choose any app that support this intent.
                    {
                        sendIntent.setPackage(defaultSmsPackageName);
                    }
                    activity.startActivity(sendIntent);

                }else {

                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", "9056916760");
                    smsIntent.putExtra("sms_body", "Hey I matched with you on gymbuddy");
                    startActivity(smsIntent);
                }

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
              if(!name.equalsIgnoreCase("Superman"))
              {
                  TextView txtName = (TextView)view.findViewById(R.id.profileName);
                  txtName.setText(selectedUser.getName());
                  TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
                  txtSummary.setText("This user has not entered a summary yet");
                  ImageView imgView = (ImageView)view.findViewById(R.id.profilePic);
                  imgView.setImageResource(R.drawable.face);
              }else{
                  TextView txtName = (TextView)view.findViewById(R.id.profileName);
                  txtName.setText("Superman");
                  TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
                  txtSummary.setText("I am the last son of the dead planet Krypton. I am looking for a gym buddy to help me train to fight Doomsday");
                  ImageView imgView = (ImageView)view.findViewById(R.id.profilePic);
                  imgView.setImageResource(R.drawable.niccage);
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
