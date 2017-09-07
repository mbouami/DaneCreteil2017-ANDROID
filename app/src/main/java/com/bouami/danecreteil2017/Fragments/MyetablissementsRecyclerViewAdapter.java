package com.bouami.danecreteil2017.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bouami.danecreteil2017.Models.Etablissement;
import com.bouami.danecreteil2017.Models.EtablissementModel;
import com.bouami.danecreteil2017.R;

import com.bouami.danecreteil2017.Fragments.EtablissementsFragment.OnListFragmentInteractionListener;
import com.bouami.danecreteil2017.Fragments.dummy.DummyContent.DummyItem;
import com.bouami.danecreteil2017.viewholder.EtablissementViewHolder;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyetablissementsRecyclerViewAdapter extends RecyclerView.Adapter<EtablissementViewHolder> {

    private final List<Etablissement> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyetablissementsRecyclerViewAdapter(List<Etablissement> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public EtablissementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_etablissements, parent, false);
        return new EtablissementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EtablissementViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNomView.setText(mValues.get(position).getType() + " " + mValues.get(position).getNom());
        holder.mTelView.setText(mValues.get(position).getTel());
        holder.mEmailView.setText(mValues.get(position).getEmail());
        holder.mVilleView.setText(mValues.get(position).getCp() + " " + mValues.get(position).getVille());

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

/*    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNomView;
        public final TextView mTelView;
        public final TextView mEmailView;
        public final TextView mVilleView;
        public EtablissementModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNomView = (TextView) view.findViewById(R.id.nom);
            mTelView = (TextView) view.findViewById(R.id.tel);
            mEmailView = (TextView) view.findViewById(R.id.email);
            mVilleView = (TextView) view.findViewById(R.id.ville);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNomView.getText() + "'";
        }
    }*/
}
