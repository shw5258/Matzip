package com.example.light.matzip.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.light.matzip.R;
import com.example.light.matzip.fragment.ResListFragment;
import com.example.light.matzip.models.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by light on 2017-03-28.
 */

public class ResListAdapter extends FirebaseRecyclerAdapter<Restaurant, ResListAdapter.ResListViewHolder> {

    ResListFragment.ListButton mListButton;


    /**
     * @param modelClass      Firebase will marshall the data at a location into
     *                        an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
*                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     * @param listener
     */
    public ResListAdapter(Class<Restaurant> modelClass, int modelLayout, Class<ResListViewHolder> viewHolderClass, Query ref, ResListFragment.ListButton listener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mListButton = listener;
    }

    @Override
    protected void populateViewHolder(ResListViewHolder viewHolder, Restaurant model, int position) {
        final DatabaseReference restauarantDataRef = getRef(position);
        viewHolder.mPrimaryKey = restauarantDataRef.getKey();
        viewHolder.mItemName.setText(model.name);
        ImageView itemImageView = viewHolder.mItemImage;
        if (model.profileUrl == null) {
            itemImageView.setImageDrawable(ContextCompat.getDrawable(itemImageView.getContext(), R.drawable.ic_store_black));
        } else {
            Glide.with(itemImageView.getContext())
                    .load(model.profileUrl)
                    .into(viewHolder.mItemImage);
        }
        viewHolder.mItemIntro.setText(model.intro);
    }

    public class ResListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private String mPrimaryKey;
        private ImageView mItemImage;
        private TextView mItemName;
        private TextView mItemIntro;

        public ResListViewHolder(View itemView) {
            super(itemView);
            mItemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            mItemName = (TextView) itemView.findViewById(R.id.itemName);
            mItemIntro = (TextView) itemView.findViewById(R.id.itemIntro);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListButton.onListClicked(mPrimaryKey);
        }
    }

}
