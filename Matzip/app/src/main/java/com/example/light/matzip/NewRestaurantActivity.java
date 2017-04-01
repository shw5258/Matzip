package com.example.light.matzip;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.light.matzip.models.Restaurant;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * "I will make you into a great nation and I will bless you
 *  I will make your name great, and you will be a blessing.
 *  I will bless those who bless you, and whoever curses you I will curse
 *  and all peoples on earth will be blessed through you."
 */

public class NewRestaurantActivity extends AppCompatActivity {

    private static final String REQUIRED = "Required";
    private static final int REQUEST_IMAGE = 1;
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 102;
    
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseToRef;
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
    private Button mImageFind;
    private String mRestaurantPrimaryKey;
    private StorageReference mFirebaseImageRef;
    private ImageView mImageView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restaurant);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseToRef = mDatabase.push();
        mRestaurantPrimaryKey = mDatabaseToRef.getKey();

        mNameField = (EditText) findViewById(R.id.field_name);
        mOwnerField = (EditText) findViewById(R.id.field_owner);
        mAdressField = (EditText) findViewById(R.id.field_address);
        mLatitudeField = (EditText) findViewById(R.id.field_latitude);
        mLongitudeField = (EditText) findViewById(R.id.field_longitude);
        mProfileUrlField = (EditText) findViewById(R.id.field_profileUrl);
        mImageView = (ImageView) findViewById(R.id.restaurantImage);
        mIntroField = (EditText) findViewById(R.id.field_intro);
        mPhoneNumberField = (EditText) findViewById(R.id.field_phoneNumber);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);
        mImageFind = (Button) findViewById(R.id.imageFind);
        
        mImageFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    
                if (!EasyPermissions.hasPermissions(NewRestaurantActivity.this, PERMS)) {
                    EasyPermissions.requestPermissions(NewRestaurantActivity.this, getString(R.string.rational_image_perm),
                            RC_IMAGE_PERMS, PERMS);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    final Uri uri = data.getData();
                    String uuid = UUID.randomUUID().toString();
                    mFirebaseImageRef = FirebaseStorage.getInstance().getReference("restaurantPhoto").child(uuid);
                    mFirebaseImageRef.putFile(uri)
                            .addOnCompleteListener(this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(NewRestaurantActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                                        mProfileUrlField.setText(mFirebaseImageRef.toString());
                                        showDownload();
                                    }
                                }
                            })
                            .addOnFailureListener(this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(NewRestaurantActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                                }
                    });
                }
            }
        }
    }
    
    private void showDownload() {
        Glide.with(this).using(new FirebaseImageLoader())
                .load(mFirebaseImageRef)
                .centerCrop()
                .crossFade()
                .into(mImageView);
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

        boolean empty = false;
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);
            empty = true;
        }
        if (TextUtils.isEmpty(owner)){
            mOwnerField.setError(REQUIRED);
            empty = true;
        }
        if (TextUtils.isEmpty(address)){
            mAdressField.setError(REQUIRED);
            empty = true;
        }
        if (TextUtils.isEmpty(latitude)){
            mLatitudeField.setError(REQUIRED);
            empty = true;
        }
        if (TextUtils.isEmpty(longitude)){
            mLongitudeField.setError(REQUIRED);
            empty = true;
        }
        if (TextUtils.isEmpty(profileUrl)){
            mProfileUrlField.setError(REQUIRED);
            empty = true;
        }
        if (TextUtils.isEmpty(intro)){
            mIntroField.setError(REQUIRED);
            empty = true;
        }
        if (TextUtils.isEmpty(phoneNumber)){
            mPhoneNumberField.setError(REQUIRED);
            empty = true;
        }
        if (TextUtils.isEmpty(password)){
            mPasswordField.setError(REQUIRED);
            empty = true;
        }
        if (empty) return;
    
        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();
        float flatitude = Float.parseFloat(latitude);
        float flongitude = Float.parseFloat(longitude);
    
        writeNewRestaurant(name, owner, address, flatitude, flongitude, profileUrl, intro, phoneNumber, password);
    }
    
    private void writeNewRestaurant(String name, String owner, String address, float flatitude, float flongitude, String profileUrl, String intro, String phoneNumber, String password) {
        String key = mDatabase.child("restaurants").push().getKey();
        Restaurant restaurant = new Restaurant(name, owner, address, flatitude, flongitude, profileUrl, intro, phoneNumber, password);
        Map<String, Object> postValues = restaurant.toMap();
    
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/restaurants/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
    
    private void setEditingEnabled(boolean enabled) {
        mNameField.setEnabled(enabled);
        mOwnerField.setEnabled(enabled);
        mAdressField.setEnabled(enabled);
        mLatitudeField.setEnabled(enabled);
        mLongitudeField.setEnabled(enabled);
        mProfileUrlField.setEnabled(enabled);
        mIntroField.setEnabled(enabled);
        mPhoneNumberField.setEnabled(enabled);
        mPasswordField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }
}
