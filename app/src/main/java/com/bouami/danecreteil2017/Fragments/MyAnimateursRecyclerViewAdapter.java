package com.bouami.danecreteil2017.Fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import com.bouami.danecreteil2017.Fragments.AnimateursFragment.OnListFragmentInteractionListener;
import com.bouami.danecreteil2017.Fragments.dummy.DummyContent.DummyItem;
import com.bouami.danecreteil2017.Models.Animateur;
import com.bouami.danecreteil2017.Models.AnimateurModel;
import com.bouami.danecreteil2017.R;
import com.bouami.danecreteil2017.viewholder.AnimateurViewHolder;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAnimateursRecyclerViewAdapter extends RecyclerView.Adapter<AnimateurViewHolder> {

//    private final List<DummyItem> mValues;
    private final List<Animateur> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final String MyAnimateursRecyclerViewAdapter_TAG = "LISTE_DES_ANIMATEURS";

    public MyAnimateursRecyclerViewAdapter(List<Animateur> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public AnimateurViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_animateurs, parent, false);
        return new AnimateurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnimateurViewHolder holder, int position) {
//        Log.w(MyAnimateursRecyclerViewAdapter_TAG, "Nom  : "+ mValues.get(position).getNom() + "--"+mValues.get(position).getEmail());
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).getId());
        holder.mNomView.setText(mValues.get(position).getPrenom() + " "+ mValues.get(position).getNom());
        holder.mTelView.setText(mValues.get(position).getTel());
        holder.mEmailView.setText(mValues.get(position).getEmail());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
