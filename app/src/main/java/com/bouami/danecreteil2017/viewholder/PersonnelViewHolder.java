package com.bouami.danecreteil2017.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bouami.danecreteil2017.Models.Personnel;
import com.bouami.danecreteil2017.R;

/**
 * Created by mbouami on 06/09/2017.
 */

public class PersonnelViewHolder extends RecyclerView.ViewHolder {
    public final TextView mNomView;
    public final TextView mStatutView;
    public Personnel mItem;
    public final View mView;

    public PersonnelViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mStatutView = (TextView) itemView.findViewById(R.id.statut);
        mView = itemView;
    }

    public void bindToPersonnel(Personnel personnel,View.OnClickListener starClickListener) {
        mNomView.setText(personnel.getGenre() + " " + personnel.getPrenom() + " " + personnel.getNom());
        mStatutView.setText(personnel.getStatut());
        mNomView.setOnClickListener(starClickListener);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }
}
