package com.example.brandon.gymbuddy;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

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
}
