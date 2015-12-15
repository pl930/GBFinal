package com.assignments.koorong.gym_buddy_alpha_;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapperConfig;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.assignments.koorong.gym_buddy_alpha_.R;
import com.assignments.koorong.gym_buddy_alpha_.SignupActivity;
import com.assignments.koorong.gym_buddy_alpha_.db.UserDataSource;

import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    //Used for early development debugging
    /*UserDataSource ds = new UserDataSource(this);*/
    ProgressDialog progressDialog;
    SessionManager sm;

    /*ButterKnife injections
    *
    * Butterknife is an android injection library which simplifies binding of UI elements to the code.
    * Read more here: http://jakewharton.github.io/butterknife/
    * Very useful however, but decided against implementing into entire project since I(Peter) wasn't the only one working with the code
    * */
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        sm = new SessionManager(getApplicationContext());


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }



    /*Login method. Calls async task LoginAuth()*/
    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);
        new LoginAuth().execute();
    }

    /*Go to MatchActivity if called*/
    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent i = new Intent(getApplicationContext(), MatchActivity.class);
        startActivity(i);
        //finish activity to prevent going back to this page
        finish();
    }

    /*failed login*/
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Wrong Username/Password", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    /*Validate user input*/
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
            _passwordText.setError("between 6 and 15 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    /*Our login async task.*/
    private class LoginAuth extends AsyncTask<Void, Void, Void> {
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        boolean loginSuccess;

        /*Show Progress Dialog*/
        public void onPreExecute(){
            progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
        }


        /*Our database call*/
        @Override
        protected Void doInBackground(Void... params) {
            try {
                /*Get Cognito User authentication credentials*/
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(), //Context
                        "us-east-1:cbaeddaa-0588-4ec5-a367-11895f99e2c8", // Identity Pool ID
                        Regions.US_EAST_1 // Region
                );
                /*DB call. Pass in credentials to verify AWS user*/
                AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
                /*Get db schema*/
                DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
                /*load logged in user from db*/
                User selectedUser = mapper.load(User.class, email);

                /*Verify password.(Not best practise... we know)*/
                if (selectedUser.getPassword().equalsIgnoreCase(password)) {
                    loginSuccess = true;
                    sm.createLoginSession(selectedUser.getName(), selectedUser.getEmail(), selectedUser.getLocation());
                } else {
                    loginSuccess = false;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d("Db Error", ex.toString());
            }
            return null;
        }

        /*Close progress dialog*/
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            if (loginSuccess) {
                onLoginSuccess();
                progressDialog.dismiss();
            } else {
                onLoginFailed();
                progressDialog.dismiss();
            }

        }
    }
}

