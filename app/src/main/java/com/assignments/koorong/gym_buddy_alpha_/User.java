package com.assignments.koorong.gym_buddy_alpha_;

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
    private String Name;
    private String location;
    private String email;
    private int Age;
    private Boolean Gender;
    private String Password;
    int AgePref;
    int genderPref;
    int experience;
    int frequency;
    int exerciseType;
    String goal;
    String picture;
    String placeHolder;

    @Override
    public String toString() {
        return
                "Name='" + Name + '\'' +
                        ", agePref='" + AgePref + '\'' +
                        ", location='" + location + '\'' +
                        ", email='" + email + '\''
                ;
    }

    @DynamoDBHashKey(attributeName = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "Password")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }



    @DynamoDBAttribute(attributeName = "Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBAttribute(attributeName = "Age")
    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    @DynamoDBAttribute(attributeName = "Gender")
    public Boolean getGender() {return Gender;}

    public void setGender(Boolean Gender) {
        this.Gender = Gender;
    }

    @DynamoDBAttribute(attributeName = "AgePref")
    public int getAgePref() {return AgePref;}

    public void setAgePref(int AgePref) {
        this.AgePref = AgePref;
    }

    @DynamoDBAttribute(attributeName = "genderPref")
    public int getgenderPref() {return genderPref;}

    public void setgenderPref(int genderPref) {
        this.genderPref = genderPref;
    }

    @DynamoDBAttribute(attributeName = "experience")
    public int getexperience() {return experience;}

    public void setexperience(int experience) {
        this.experience = experience;
    }

    @DynamoDBAttribute(attributeName = "frequency")
    public int getfrequency() {return frequency;}

    public void setfrequency(int frequency) {
        this.frequency = frequency;
    }

    @DynamoDBAttribute(attributeName = "exerciseType")
    public int getexerciseType() {return exerciseType;}

    public void setexerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }

    @DynamoDBAttribute(attributeName = "goal")
    public String getgoal() {return goal;}

    public void setgoal(String goal) {
        this.goal = goal;
    }

    @DynamoDBAttribute(attributeName = "picture")
    public String getpicture() {return picture;}

    public void setpicture(String picture) {
        this.picture = picture;
    }

    @DynamoDBAttribute(attributeName = "placeHolder")
    public String getplaceHolder() {return placeHolder;}

    public void setplaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

}
