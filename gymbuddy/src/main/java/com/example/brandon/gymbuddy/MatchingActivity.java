package com.example.brandon.gymbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MatchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_matching, menu);
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

    //This Matcher Method should be called with a click which calls the match method below it
    public void matcher(View v)
    {   //create test users
        /**
         * @param email = validated email address
         * @param name = First and last name
         * @param age = Age of user
         * @param gender = Male/Female
         * @param location = place of residence
         * @param genderPref = Gender they prefer
         * @param exercisePref = Names of Exercises user wants to focus on
         */
/*
        User searchingUser = new User("popeye@gmail.com",
                "John Doe",//name
                25,        //age
                "Male",    //gender
                "Ontario", //location
                "Male",    //genderPref
                new String[] {"Cardio", "Crossfit","Endurance"}
        );

        ArrayList<User> userPool = new ArrayList<>();

        User testUser1 = new User("joe@gmail.com",
                "joe T",//name
                35,        //age
                "Male",          //
                "Ontario",
                "Male",
                new String[] {"Cardio"}
        );
        User testUser2 = new User("sam@gmail.com",
                "Sam M",//name
                22,        //age
                "Male",          //
                "Ontario",
                "Both",
                new String[] {"Endurance"}
        );

        User testUser3 = new User("tina@gmail.com",
                "tina fey",//name
                22,        //age
                "Female",          //
                "Ontario",
                "Female",
                new String[] {"Cardio"}
        );

        User testUser4 = new User("Jasmine@gmail.com",
                "Jasmine Smith",//name
                30,        //age
                "Female",          //
                "Ontario",
                "Female",
                new String[] {"Cardio"}
        );

        User testUser5 = new User("tim@gmail.com",
                "Tim Boyd",//name
                55,        //age
                "Male",  //
                "Ontario",
                "Male",
                new String[] {"Cardio", "Crossfit"}
        );

        User testUser6 = new User("bobby@gmail.com",
                "Bobby Taylor",//name
                58,        //age
                "Male",  //
                "Ontario",
                "Male",
                new String[] {"Cardio", "Crossfit"}
        );
        User testUser7 = new User("bobby@gmail.com",
                "Bobby Taylor",//name
                26,        //age
                "Male",  //
                "Ontario",
                "Male",
                new String[] {"Cardio", "Crossfit"}
        );
        User testUser8 = new User("ada@gmail.com",
                "Ada Sharp",//name
                30,        //age
                "Female",  //
                "Ontario",
                "Male",
                new String[] {"Cardio", "Crossfit"}
        );
        userPool.add(testUser1);
        userPool.add(testUser2);
        userPool.add(testUser3);
        userPool.add(testUser4);
        userPool.add(testUser5);
        userPool.add(testUser6);
        userPool.add(testUser7);
        userPool.add(testUser8);
        /**
         * Email
         * Name
         * Age
         * Gender   // Enum value
         * Location
         * GenderPref:  // Enum value
         *    FEMALE  (1)
         *    MALE    (2)
         *    BOTH    (4)
         * ExercisePref:
         *
         CALESTHETICS   (1)
         WEIGHT_LIFTING (2)
         CROSSFIT       (4)
         CARDIO         (8)
         ENDURANCE      (16)
         >>>> combinations of the above numbers are the Exercise preference relations and are
         what the calculations are based on<<<<<<

         User searchingUser = new User("popeye",
         "John Doe",
         25,
         ,
         "Ontario",
         Gender.BOTH,
         );

        match(userPool,searchingUser);*/
    }

    public void match(ArrayList<User> users, User user)
    {
      /*  ArrayList<User> matches = new ArrayList<User>();
        //look through users, check for info
        for(int i = 0; i < users.size(); i++)
        {
            User testUser = users.get(i);
            //check age proximity first. if user is older or younger by 10 years they are removed from matches
            if((testUser.getAge() >= user.getAge() + 10) || (testUser.getAge() <= user.getAge() - 10))
            {
                //go to next loop iteration
                continue;
            }
            //Does user prefer both gender
            if(user.getGenderPrefString().equals("Both") && testUser.getGenderPrefString().equals("Both"))
            {
                //gender match
            }
            else if(user.getGenderPrefString().equals("Male") &&
                    !(testUser.getGenderPrefString().equals("Female")))
            {
                //gender match
            }
            else if(user.getGenderPrefString().equals("Female") && !(testUser.getGenderPrefString().equals("Male")))
            {
                //gender match
            }
            else
            {
                //there is no gender match and so move to the next user
                continue;
            }

            //now move on to check the user's exercise preferences
            for(int j=0; j < user.getExercisePref().length; j++)
            {
                //look through current Test User's preferences
                for(int x = 0; x < testUser.getExercisePref().length; x++)
                {
                    //if an exercise preference is found, increase match count
                    if(user.getExercisePref()[j].equals(testUser.getExercisePref()[x]))
                    {
                        //one match found!
                        testUser.increaseMatchCount();
                    }
                }
            }
            //if there is a match, add to the matches list
            if(testUser.getMatchCount() > 0){matches.add(testUser);}
        }   //end of userpool and match search
        TextView matchText = (TextView) findViewById(R.id.matchText);


        //display matches
        for(int z = 0; z < matches.size();z++)
        {
            User crnt = matches.get(z);
            matchText.append("Email: " + crnt.getEmail() + "\n" +
                            "Name: " + crnt.getName() + "\n" +
                            "Age: " + crnt.getAge() + "\n" +
                            "Gender: " + crnt.getGender() + "\n" +
                            "Location: " + crnt.getLocation() + "\n" +
                            "\n|-----------------------|\n"
            );
        }*/
    }




}
