package com.example.light.matzip.models;

import android.support.annotation.Nullable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username;
    public String nickname;
    public String email;
    public String phonenumber;
    public String profileUrl;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String nickname, String email,@Nullable String phonenumber,@Nullable String profileUrl) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.profileUrl = profileUrl;
    }
}
