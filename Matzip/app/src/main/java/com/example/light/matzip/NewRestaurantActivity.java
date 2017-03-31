package com.example.light.matzip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by light on 2017-03-29.
 */

class NewRestaurantActivity extends AppCompatActivity{

    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;
    private EditText mNameField;
    private EditText mOwnerField;
    private EditText mAdressField;
    private EditText mLatitudeField;
    private EditText mLongitudeField;
    private EditText mProfileUrlField;
    private EditText mIntroField;
    private EditText mPhoneNumberField;
    private EditText mPasswordField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restaurant);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameField = (EditText) findViewById(R.id.field_name);
        mOwnerField = (EditText) findViewById(R.id.field_owner);
        mAdressField = (EditText) findViewById(R.id.field_address);
        mLatitudeField = (EditText) findViewById(R.id.field_latitude);
        mLongitudeField = (EditText) findViewById(R.id.field_longitude);
        mProfileUrlField = (EditText) findViewById(R.id.field_profileUrl);
        mIntroField = (EditText) findViewById(R.id.field_intro);
        mPhoneNumberField = (EditText) findViewById(R.id.field_phoneNumber);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String name = mNameField.getText().toString();
        final String owner = mOwnerField.getText().toString();
        final String address = mAdressField.getText().toString();
        final String latitude = mLatitudeField.getText().toString();
        final String longitude = mLongitudeField.getText().toString();
        final String profileUrl = mProfileUrlField.getText().toString();
        final String intro = mIntroField.getText().toString();
        final String phoneNumber = mPhoneNumberField.getText().toString();
        final String password = mPasswordField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);

        } else if (TextUtils.isEmpty(owner)){
            mOwnerField.setError(REQUIRED);
            return;
        } else if (TextUtils.isEmpty(address)){
            mAdressField.setError(REQUIRED);
            return;
        } else if (TextUtils.isEmpty(latitude)){
            mLatitudeField.setError(REQUIRED);
            return;
        } else if (TextUtils.isEmpty(longitude)){
            mLongitudeField.setError(REQUIRED);
            return;
        } else if (TextUtils.isEmpty(profileUrl)){
            mProfileUrlField.setError(REQUIRED);
            return;
        } else if (TextUtils.isEmpty(intro)){
            mIntroField.setError(REQUIRED);
            return;
        } else if (TextUtils.isEmpty(phoneNumber)){
            mPhoneNumberField.setError(REQUIRED);
            return;
        } else if (TextUtils.isEmpty(password)){
            mPasswordField.setError(REQUIRED);
            return;
        }
    }
}
