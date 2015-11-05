package com.example.brandon.gymbuddy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Brandon on 10/21/2015.
 */
public class ShowService extends IntentService {

    private static final String TAG = "DownloadService";
    public static final String BROADCAST_ACTION = "DownloadComplete";
    public static final String BROADCAST_MESSAGE = "message";

    public ShowService(){
        super(TAG);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, ShowService.class);

    }

    @Override
    protected void onHandleIntent(Intent intent){
        URL url; InputStream inputStream = null; String message = null;

       /* private static ArrayList<User> fetchItems(View view, String location) {
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
        }*/

    }

    private String getMessageFromStream(InputStream inputStream) throws IOException{
        String message;
        byte[] buffer = new byte[inputStream.available()];
        if (inputStream.read(buffer)>0){
            return parseStudentGradeFile(new String(buffer));
        }else{
            message = "No data found";
        }
        return message;
    }

    private String parseStudentGradeFile(String jsonData)
    {
        StringBuilder studentBuilder = new StringBuilder();

        try{
            JSONObject wrapper = new JSONObject(jsonData);
            JSONObject studentInfo = wrapper.getJSONObject("student-grades");
            studentBuilder.append("Name: ")
                    .append(studentInfo.getString("name"))
                    .append("\n");
            if(studentInfo.getBoolean("full-time")){
                studentBuilder.append("Status: Full Time \n\n");
            }else{
                studentBuilder.append("Status: Part Time \n\n");
            }

            JSONArray courseList = studentInfo.getJSONArray("courses");
            for (int i = 0; i < courseList.length(); i++){
                JSONObject course = courseList.getJSONObject(i);
                studentBuilder.append(course.get("name"))
                        .append(": ")
                        .append(course.getString("grade"))
                        .append("\n");
            }


        } catch(JSONException e){
            Log.e(TAG, e.toString());
        }

        return studentBuilder.toString();
    }

    private void sendMessageBroadcast(String message) {

        //create a new intent
        Intent intent = new Intent();

        //set extra and action using constants defined above
        intent.putExtra(BROADCAST_MESSAGE, message);
        intent.setAction(BROADCAST_ACTION);

        //send the broadcast
        getApplicationContext().sendBroadcast(intent);
    }

}
