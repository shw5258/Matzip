package com.example.light.matzip.models;


import android.support.annotation.Nullable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Restaurant {

    public String mName;
    public String mOwner;
    public String mAdress;
    public float mLatitude;
    public float mLongitude;
    public String mProfileUrl;
    public String mIntro;
    public String mPhoneNumber;
    public String mPassword;

    public Restaurant(){

    }

    public Restaurant(String name, String owner, String address, float latitude, float longitude,
                      String profileUrl, String intro, String phonenumber, String password){
        this.mName = name;
        this.mOwner = owner;
        this.mAdress = address;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mProfileUrl = profileUrl;
        this.mIntro = intro;
        this.mPhoneNumber = phonenumber;
        this.mPassword = password;
    }
}
