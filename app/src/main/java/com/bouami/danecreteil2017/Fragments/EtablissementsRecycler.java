package com.bouami.danecreteil2017.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017.Models.Etablissement;
import com.bouami.danecreteil2017.NewReferentActivity;
import com.bouami.danecreteil2017.PersonnelParEtablissementActivity;
import com.bouami.danecreteil2017.R;
import com.bouami.danecreteil2017.viewholder.EtablissementViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

/**
 * Created by mbouami on 08/09/2017.
 */

public class EtablissementsRecycler extends FirebaseRecyclerAdapter<Etablissement,EtablissementViewHolder> {
    /**
     * @param modelClass      Firebase will marshall the data at a location into
     *                        an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */

    private static final String TAG = "EtablissementsRecycler";
    private DatabaseReference mDatabase;
    private FloatingActionButton mailetablissement;
    private FloatingActionButton phoneetablissement;
    private FloatingActionButton addreferentetablissement;
    private Etablissement etablissementselectionne;

    public EtablissementsRecycler(Class<Etablissement> modelClass, int modelLayout, Class<EtablissementViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public EtablissementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mailetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailetablissement);
        mailetablissement.setVisibility(View.INVISIBLE);
        mailetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(createMailIntent(etablissementselectionne.getEmail()));
            }
        });
        phoneetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.phoneetablissement);
        phoneetablissement.setVisibility(View.INVISIBLE);
        phoneetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(createPhoneIntent(etablissementselectionne.getTel()));
            }
        });
        addreferentetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.addreferent);
        addreferentetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(createPhoneIntent(etablissementselectionne.getTel()));
                Intent intent = new Intent(view.getContext(), NewReferentActivity.class);
                intent.putExtra(NewReferentActivity.EXTRA_ETABLISSEMENT_KEY, "0931192R");
                view.getContext().startActivity(intent);
            }
        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void populateViewHolder(EtablissementViewHolder viewHolder, Etablissement model, int position) {
        final DatabaseReference etablissementRef = getRef(position);

        // Set click listener for the whole post view
        final String etablissementKey = etablissementRef.getKey();
                viewHolder.mListePersonnelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(v.getContext(), PersonnelParEtablissementActivity.class);
                        intent.putExtra(PersonnelParEtablissementActivity.EXTRA_ETABLISSEMENT_KEY, etablissementKey);
                        v.getContext().startActivity(intent);
                    }
                });

        // Determine if the current user has liked this post and set UI accordingly
/*                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }*/

        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToEtablissement(model, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                // Need to write to both places the post is stored
                DatabaseReference globalEtablissementRef = mDatabase.child("etablissements").child(etablissementKey);
                // Run two transactions
                onStarClicked(globalEtablissementRef);
//                        onStarClicked(userPostRef);
            }
        });
    }

    private Intent createShareIntent(String mNomEtabcast, String mEtabSharecast) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Carte visite : "+mNomEtabcast);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mEtabSharecast);
        return shareIntent;
    }

    private Intent createPhoneIntent(String mTelEtabcast) {
        Intent shareIntent = new Intent(Intent.ACTION_DIAL);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setData(Uri.parse("tel:"+mTelEtabcast));
        return shareIntent;
    }

    private Intent createMailIntent(String mMailEtabcast) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { mMailEtabcast });
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande de rendez-vous");
        return shareIntent;
    }

    private Intent createMapIntent(String mAdresseEtabcast) {
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", mAdresseEtabcast)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        return intent;
    }

    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference etablissementRef) {
        etablissementRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Etablissement p = mutableData.getValue(Etablissement.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

/*                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }*/

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                etablissementselectionne = dataSnapshot.getValue(Etablissement.class);
                if (mailetablissement.getVisibility()==View.INVISIBLE) {
                    mailetablissement.setVisibility(View.VISIBLE);
                    phoneetablissement.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "postTransaction:onComplete:" + dataSnapshot.getValue(Etablissement.class).getNom());
            }
        });
    }
    // [END post_stars_transaction]

}
