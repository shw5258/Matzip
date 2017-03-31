package com.example.light.matzip.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.light.matzip.MainActivity;
import com.example.light.matzip.R;
import com.example.light.matzip.models.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IntroducingFragment extends Fragment {

    public static final String INDEX = "restaurant_list_index";
    private static final String TAG = IntroducingFragment.class.getSimpleName();

    private DatabaseReference mRestaurantRef;
    private DatabaseReference mSpecificRestaurantRef;
    private TextView mRestaurantName;
    private TextView mOwnerName;
    private TextView mAdress;
    private TextView mComment;
    private TextView mCoordinate;
    private TextView mResIntro;
    private TextView mProfileImage;
    private TextView mPhoneNumber;
    private TextView mPassword;

    private ValueEventListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String primaryKey = getArguments().getString(INDEX);
        mRestaurantRef = FirebaseDatabase.getInstance().getReference(MainActivity.RESTAURANT_CHILD);
        mSpecificRestaurantRef = mRestaurantRef.child(primaryKey);

        View view = inflater.inflate(R.layout.fragment_introducing, container, false);
        mRestaurantName = (TextView) view.findViewById(R.id.resName);
        mOwnerName = (TextView) view.findViewById(R.id.ownerName);
        mAdress = (TextView) view.findViewById(R.id.address);
        mComment = (TextView) view.findViewById(R.id.comment);
        mCoordinate = (TextView) view.findViewById(R.id.coordinate);
        mResIntro = (TextView) view.findViewById(R.id.resIntro);
        mProfileImage = (TextView) view.findViewById(R.id.profileImage);
        mPhoneNumber = (TextView) view.findViewById(R.id.phoneNum);
        mPassword = (TextView) view.findViewById(R.id.password);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        ValueEventListener changeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                mRestaurantName.setText(restaurant.name);
                mOwnerName.setText(restaurant.owner);
                mAdress.setText(restaurant.address);
                mComment.setText(Float.toString(restaurant.latitude));
                mCoordinate.setText(Float.toString(restaurant.longitude));
                mResIntro.setText(restaurant.intro);
                mProfileImage.setText(restaurant.profileUrl);
                mPhoneNumber.setText(restaurant.phonenumber);
                mPassword.setText(restaurant.password);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "load Restaurant Info :onCancelled", databaseError.toException());
                Toast.makeText(getActivity(), "Failed to load post.", Toast.LENGTH_SHORT).show();
            }
        };

        mSpecificRestaurantRef.addValueEventListener(changeListener);
        mListener = changeListener;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            mSpecificRestaurantRef.removeEventListener(mListener);
        }
    }
}
