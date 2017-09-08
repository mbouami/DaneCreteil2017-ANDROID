package com.bouami.danecreteil2017.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bouami.danecreteil2017.R;
import com.bouami.danecreteil2017.Models.Etablissement;

/**
 * Created by mbouami on 02/09/2017.
 */

public class EtablissementViewHolder extends RecyclerView.ViewHolder {

    public final TextView mNomView;
    public final TextView mTelView;
    public final TextView mEmailView;
    public final TextView mVilleView;
    public Etablissement mItem;
    public final View mView;

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }

    public EtablissementViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mTelView = (TextView) itemView.findViewById(R.id.tel);
        mEmailView = (TextView) itemView.findViewById(R.id.email);
        mVilleView = (TextView) itemView.findViewById(R.id.ville);
        mView = itemView;
    }

    public void bindToEtablissement(Etablissement etablissement,View.OnClickListener starClickListener) {
        mNomView.setText(etablissement.getType() + " " + etablissement.getNom());
        mTelView.setText(etablissement.getTel());
        mEmailView.setText(etablissement.getEmail());
        mVilleView.setText(etablissement.getCp() + " " + etablissement.getVille());
        mView.setOnClickListener(starClickListener);
    }
}
