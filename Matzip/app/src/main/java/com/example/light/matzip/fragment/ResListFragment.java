package com.example.light.matzip.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.light.matzip.MainActivity;
import com.example.light.matzip.R;
import com.example.light.matzip.adapter.ResListAdapter;
import com.example.light.matzip.models.Restaurant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResListFragment extends Fragment {

    public static final String TAG = ResListFragment.class.getSimpleName();


    private DatabaseReference mFirebaseDatabaseReference;
    private ListButton mListener;

    public interface ListButton {
        void onListClicked(String primaryKey);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mListener = (ListButton) getActivity();

        View view = inflater.inflate(R.layout.fragment_res_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.resListRecyclerView);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.RESTAURANT_CHILD);
        ResListAdapter adapter = new ResListAdapter(
                Restaurant.class,
                R.layout.res_list_item,
                ResListAdapter.ResListViewHolder.class,
                mFirebaseDatabaseReference,
                mListener);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener = null;
    }
}

