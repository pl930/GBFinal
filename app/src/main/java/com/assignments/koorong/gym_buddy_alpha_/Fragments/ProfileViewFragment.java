package com.assignments.koorong.gym_buddy_alpha_.Fragments;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
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
        Toast.makeText(getActivity(), "Email: " + email, Toast.LENGTH_LONG).show();

//        loading = new ProgressDialog(view.getContext(), R.style.AppTheme_Dark_Dialog);
//        loading.setCancelable(false);
//        loading.setMessage("Getting Matches");
//        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

       // new getItems().execute();

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
//            TextView txtName = (TextView)view.findViewById(R.id.matchName2);
//            txtName.setText(selectedUser.getName());
//            TextView txtSummary = (TextView)view.findViewById(R.id.matchSummary2);
//            if(selectedUser.getgoal() != null)
//            {
//                txtSummary.setText(selectedUser.getgoal());
//            }
        }

}
