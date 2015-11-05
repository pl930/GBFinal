package com.example.brandon.gymbuddy;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //adds a user
    public void dynamoB(View view)
    {

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);

        User user = new User();
//        book.setTitle("Great Expectations");
//        book.setAuthor("Charles Dickens");
//        book.setPrice(1299);
//        book.setIsbn("1234567890");
        EditText email = (EditText) findViewById(R.id.txtEmail);
        EditText fname = (EditText) findViewById(R.id.txtFirstName);
        EditText lname = (EditText) findViewById(R.id.txtLastName);
        EditText location = (EditText) findViewById(R.id.txtLocation);
          user.setEmail(email.getText().toString());
          user.setFirstName(fname.getText().toString());
          user.setLastName(lname.getText().toString());
          user.setLocation(location.getText().toString());
//        book.setHardCover(false);
        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
        mapper.save(user);
        //}catch(Exception ex){
        //     Log.d("myTag", ex.toString());
        //}
    }
    //shows a single user, hardcoded
    public void show(View view)
    {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
        User selectedBook = mapper.load(User.class, "peter@gmail.com");
        TextView display = (TextView) findViewById(R.id.txtList);
        display.setText(selectedBook.getFirstName());
    }
    //shows all users or all users in a location
    public void show2(View view){
        EditText location = (EditText) findViewById(R.id.txtLocation);
        ArrayList<User> ids = fetchItems(view, location.getText().toString());
        TextView display = (TextView) findViewById(R.id.txtList);
        display.setText(ids.toString());
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
