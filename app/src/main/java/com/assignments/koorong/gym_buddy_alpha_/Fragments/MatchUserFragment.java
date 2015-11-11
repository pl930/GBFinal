package com.assignments.koorong.gym_buddy_alpha_.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.assignments.koorong.gym_buddy_alpha_.R;
import com.assignments.koorong.gym_buddy_alpha_.SessionManager;
import com.assignments.koorong.gym_buddy_alpha_.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MatchUserFragment extends Fragment {
    SessionManager sm;

    public MatchUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_user, container, false);
        sm = new SessionManager(getActivity().getApplicationContext());
        fetchItems(view, sm.getUserDetails().get("KEY_LOCATION"));
        MatchUserAdapter adapter = new MatchUserAdapter(getActivity().getApplicationContext(), R.layout.match_user_item, fetchItems(view, sm.getUserDetails().get("KEY_LOCATION")));
        ListView userList = (ListView) getActivity().findViewById(R.id.UserMatches);
        userList.setAdapter(adapter);

        return view;
    }

    //returns an ArrayList of users
    private static ArrayList<User> fetchItems(View view, String location) {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                view.getContext(),
                "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );

        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);


        ArrayList<User> ids = new ArrayList<User>();

        ScanResult result = null;

        do{
            ScanRequest req = new ScanRequest();
            req.setTableName("Users");

            if(result != null){
                req.setExclusiveStartKey(result.getLastEvaluatedKey());
            }

            result = ddbClient.scan(req);

            List<Map<String, AttributeValue>> rows = result.getItems();

            for(Map<String, AttributeValue> map : rows){
                try{
                    User user = new User();
                    AttributeValue v = map.get("Location");
                    if (!location.isEmpty()){
                        if (v.getS().equalsIgnoreCase(location)) {
                            user.setLocation(v.getS());
                            v = map.get("FirstName");
                            user.setFirstName(v.getS());
                            v = map.get("LastName");
                            user.setLastName(v.getS());
                            v = map.get("Email");
                            user.setEmail(v.getS());
                            ids.add(user);
                        }
                    }else{
                        user.setLocation(v.getS());
                        v = map.get("FirstName");
                        user.setFirstName(v.getS());
                        v = map.get("LastName");
                        user.setLastName(v.getS());
                        v = map.get("Email");
                        user.setEmail(v.getS());
                        ids.add(user);
                    }
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                }
            }
        } while(result.getLastEvaluatedKey() != null);


        //System.out.println("Result size: " + ids.size());

        return ids;
    }


}
