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
    private String LastName;
    private String FirstName;
    private String location;
    private String email;
    private String Age;
    private String Gender;
    private Boolean weightTrain;
    private Boolean cardio;
    private Boolean crossfit;
    private String Password;

    @Override
    public String toString() {
        return
                "LastName='" + LastName + '\'' +
                        ", FirstName='" + FirstName + '\'' +
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

    @DynamoDBAttribute(attributeName = "FirstName")
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    @DynamoDBAttribute(attributeName = "LastName")
    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    @DynamoDBAttribute(attributeName = "Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBAttribute(attributeName = "Age")
    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    @DynamoDBAttribute(attributeName = "Gender")
    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    @DynamoDBAttribute(attributeName = "weightTrain")
    public Boolean getWeightTrain() {
        return weightTrain;
    }

    public void setGender(Boolean weightTrain) {
        this.weightTrain = weightTrain;
    }

    @DynamoDBAttribute(attributeName = "cardio")
    public Boolean getCardio() {
        return cardio;
    }

    public void setCardio(Boolean cardio) {
        this.cardio = cardio;
    }

    @DynamoDBAttribute(attributeName = "crossfit")
    public Boolean getCrossfit() {
        return crossfit;
    }

    public void setCrossfit(Boolean crossfit) {
        this.crossfit = crossfit;
    }
}

