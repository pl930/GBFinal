package com.example.brandon.gymbuddy;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by Brandon on 10/14/2015.
 */
@DynamoDBTable(tableName = "Users")

public class User {
    private String LastName;
    private String FirstName;
    private String location;
    private String email;
    //private Boolean hardCover;

    /******************************************************************************
     * Mike's Notes *                                                             *
     * TODO: Add Members Below                                                    *
     *                                                                            *
     * private gender                                                             *
     * gender preferences: String[] mGenderPreferences                            *
     *                                                                            *
     * private age                                                                *
     * exercise preferences: String[] mExercisePreferences                        *
     *                                                                            *
     * importance of preferences                                                  *
     *                                                                            *
     *****************************************************************************/

    @Override
    public String toString() {
        return
                "LastName='" + LastName + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", location='" + location + '\'' +
                ", email='" + email + '\''
                ;
    }

    @DynamoDBIndexRangeKey(attributeName = "LastName")
    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    @DynamoDBIndexHashKey(attributeName = "FirstName")
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    @DynamoDBAttribute(attributeName = "Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBHashKey(attributeName = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    @DynamoDBAttribute(attributeName = "Hardcover")
//    public Boolean getHardCover() {
//        return hardCover;
//    }
//
//    public void setHardCover(Boolean hardCover) {
//        this.hardCover = hardCover;
//    }
}

