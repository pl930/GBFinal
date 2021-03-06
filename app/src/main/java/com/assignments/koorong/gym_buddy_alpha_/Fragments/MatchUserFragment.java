package com.assignments.koorong.gym_buddy_alpha_.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.assignments.koorong.gym_buddy_alpha_.ProfileActivity;
import com.assignments.koorong.gym_buddy_alpha_.R;
import com.assignments.koorong.gym_buddy_alpha_.SessionManager;
import com.assignments.koorong.gym_buddy_alpha_.User;
import com.assignments.koorong.gym_buddy_alpha_.ViewProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatchUserFragment extends ListFragment {
    SessionManager sm;
    Activity activity;
    ProgressDialog loading;
    OnItemSelectedListener listener;

    public MatchUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //prevent app view from switching to horizontal
        this.getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_user, container, false);
        FragmentManager fm = getFragmentManager();


        sm = new SessionManager(getActivity().getApplicationContext());
        activity = getActivity();
        loading = new ProgressDialog(view.getContext(), R.style.AppTheme_Dark_Dialog);
        loading.setCancelable(false);
        loading.setMessage("Getting Matches");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        new getItems().execute();
       // displayIds(view);

        return view;
    }


    private class getItems extends AsyncTask<Void, Void, Void> {
        ArrayList<User> ids;
        User selectedUser;
        String location;
        Boolean error = false;
        public void onPreExecute() {
            loading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ids = new ArrayList<>();
            try {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getActivity().getApplicationContext(),
                        "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                        Regions.US_EAST_1 // Region
                );
                AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
                location = sm.getUserDetails().getLocation();
                DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
                selectedUser = mapper.load(User.class, sm.getUserDetails().getEmail());
                ScanResult result = null;

                do {
                    ScanRequest req = new ScanRequest();
                    req.setTableName("Users");

                    if (result != null) {
                        req.setExclusiveStartKey(result.getLastEvaluatedKey());
                    }

                    result = ddbClient.scan(req);

                    List<Map<String, AttributeValue>> rows = result.getItems();

                    for (Map<String, AttributeValue> map : rows) {
                        try {
                            User user = new User();
                            AttributeValue v = map.get("Location");
                            if (!location.isEmpty()) {
                                if (v.getS().equalsIgnoreCase(location)) {
                                    user.setLocation(v.getS());
                                    v = map.get("Name");
                                    user.setName(v.getS());
                                    v = map.get("Email");
                                    user.setEmail(v.getS());
                                    v = map.get("Age");
                                    user.setAge(Integer.parseInt(v.getN()));
                                    v = map.get("AgePref");
                                    user.setAgePref(Integer.parseInt(v.getN()));
                                    v = map.get("experience");
                                    user.setexperience(Integer.parseInt(v.getN()));
                                    v = map.get("frequency");
                                    user.setfrequency(Integer.parseInt(v.getN()));
                                    v = map.get("genderPref");
                                    user.setgenderPref(Integer.parseInt(v.getN()));
                                    v = map.get("Gender");
                                    boolean mybool2 = false;
                                    int mybool = Integer.parseInt(v.getN());
                                    if(mybool> 0 )
                                    {
                                        mybool2 = true;
                                    }
                                    user.setGender(mybool2);
                                    ids.add(user);
                                }
                            } else {
                                user.setLocation(v.getS());
                                v = map.get("Name");
                                user.setName(v.getS());
                                v = map.get("Email");
                                user.setEmail(v.getS());
                                v = map.get("Age");
                                user.setAge(Integer.parseInt(v.getN()));
                                v = map.get("AgePref");
                                user.setAgePref(Integer.parseInt(v.getN()));
                                v = map.get("experience");
                                user.setexperience(Integer.parseInt(v.getN()));
                                v = map.get("frequency");
                                user.setfrequency(Integer.parseInt(v.getN()));
                                v = map.get("genderPref");
                                user.setgenderPref(Integer.parseInt(v.getN()));
                                v = map.get("Gender");
                                user.setGender(v.getBOOL());
                                ids.add(user);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } while (result.getLastEvaluatedKey() != null);
            } catch (AmazonClientException e) {
                error = true;
                System.out.println("Connection Error: " + e);
                e.printStackTrace();
            }


            //System.out.println("Result size: " + ids.size());

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if(error){
                loading.dismiss();
                Toast.makeText(getActivity(), "Connection Error. Try Again", Toast.LENGTH_SHORT).show();

            }else {
                loading.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), location, Toast.LENGTH_SHORT).show();
                compare(ids, selectedUser);
                //displayIds(getView(),ids);
            }
        }
    }
    private void compare(ArrayList<User> ids, User selectedUser)
    {
        int match = 0;

        for (int i = 0; i < ids.size(); i++) {
            if (selectedUser.getEmail().equalsIgnoreCase(ids.get(i).getEmail()))
            {
                ids.remove(i);
                continue;
            }
           /* if(selectedUser.getgenderPref() == ids.get(i).getgenderPref())
            {
                match = match+15;
            }*/
            if(selectedUser.getgenderPref() == 2)  //you have no preference
            {

            }else if(selectedUser.getgenderPref() == 1){ //looking for female
                if(!ids.get(i).getGender()){  //not female
                    ids.remove(i);
                    continue;
                }


            }else{//looking for male
                if(ids.get(i).getGender()){  //not male
                    ids.remove(i);
                    continue;
                }
            }


            if(selectedUser.getfrequency() == ids.get(i).getfrequency())
            {
                match = match+2;
            }

            if(selectedUser.getexperience() == ids.get(i).getexperience())
            {
                match = match+5;
            }

            if(selectedUser.getAgePref() == ids.get(i).getAgePref())  //needs to be fixed
            {
                match = match+5;
            }
            ids.get(i).setexerciseType(match);
            match = 0;
        }



        //Collections.sort(ids, new CustomComparator());
        Collections.sort(ids);
        displayIds(getView(),ids);
    }


    private void displayIds(View view, ArrayList<User> ids) {
        MatchUserAdapter adapter = new MatchUserAdapter(view.getContext(), R.layout.match_user_item, ids);
        ListView userList = (ListView)view.findViewById(android.R.id.list);
        userList.setAdapter(adapter);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface OnItemSelectedListener{
        public void onItemSelected(String email);
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView txtEmail = (TextView)v.findViewById(R.id.txtEmail);
        String email = txtEmail.getText().toString();
        listener.onItemSelected(email);
    }
}
