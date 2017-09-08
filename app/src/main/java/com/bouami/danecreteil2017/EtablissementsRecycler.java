package com.bouami.danecreteil2017;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017.Models.Etablissement;
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
                Snackbar.make(view, "Prévoire une action pour envoyer un mail à " + etablissementselectionne.getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        phoneetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.phoneetablissement);
        phoneetablissement.setVisibility(View.INVISIBLE);
        phoneetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour téléphoner à " + etablissementselectionne.getNom(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void populateViewHolder(EtablissementViewHolder viewHolder, Etablissement model, int position) {
        final DatabaseReference etablissementRef = getRef(position);

        // Set click listener for the whole post view
        final String etablissementKey = etablissementRef.getKey();
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Launch PostDetailActivity
///*                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
//                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
//                        startActivity(intent);*/
//                        Log.d(TAG, "setOnClickListener:" + etablissementKey);
//                        etablissementselectionne = model;
//                        mailetablissement.setVisibility(View.VISIBLE);
//                        phoneetablissement.setVisibility(View.VISIBLE);
//                    }
//                });

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
