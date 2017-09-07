package com.bouami.danecreteil2017.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bouami.danecreteil2017.R;
import com.bouami.danecreteil2017.Models.Animateur;

/**
 * Created by mbouami on 02/09/2017.
 */

public class AnimateurViewHolder extends RecyclerView.ViewHolder {
    public final TextView mNomView;
    public final TextView mTelView;
    public final TextView mEmailView;
    public final ImageView mPhotoView;
    public Animateur mItem;
    public final View mView;

    public AnimateurViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mTelView = (TextView) itemView.findViewById(R.id.tel);
        mEmailView = (TextView) itemView.findViewById(R.id.email);
        mPhotoView = (ImageView) itemView.findViewById(R.id.photo);
        mView = itemView;
    }

    public void bindToAnimateur(Animateur animateur,View.OnClickListener starClickListener) {
        mNomView.setText(animateur.getGenre() + " " + animateur.getPrenom() + " " + animateur.getNom());
        mTelView.setText(animateur.getTel());
        mEmailView.setText(animateur.getEmail());
        mView.setOnClickListener(starClickListener);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }
}
