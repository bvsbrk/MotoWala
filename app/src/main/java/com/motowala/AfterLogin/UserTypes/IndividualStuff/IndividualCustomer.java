package com.motowala.AfterLogin.UserTypes.IndividualStuff;

/*
 * Created by bk on 15-06-2017.
 * It is created for Wheelo
 */

import java.util.ArrayList;

public class IndividualCustomer {
    public String name="not provided";
    public String email="not provided";
    public String mobile="not provided";           // Required to get this
    public String userToken="not provided";
    public ArrayList<String> userCar=new ArrayList<>();         // Required to get this
    public String host="not provided";
    public double latitude=0;            // Required to get this
    public double longitude=0;           // Required to get this
    public String cityName="notProvided";  // Required to Get this
    public String userType="not provided";
    public String imageLink="not provided";
    public boolean userValidated=false;
    public String featureName="not provided";
}
