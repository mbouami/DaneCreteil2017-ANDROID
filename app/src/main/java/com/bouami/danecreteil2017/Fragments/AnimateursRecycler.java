package com.bouami.danecreteil2017.Fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017.EtablissementsParAnimateurActivity;
import com.bouami.danecreteil2017.Models.Animateur;
import com.bouami.danecreteil2017.R;
import com.bouami.danecreteil2017.viewholder.AnimateurViewHolder;
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

public class AnimateursRecycler extends FirebaseRecyclerAdapter<Animateur,AnimateurViewHolder> {
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
    private static final String TAG = "AnimateursRecycler";
    private DatabaseReference mDatabase;
    private Animateur animateurselectionne;
    private FloatingActionButton mailanimateur;
    private FloatingActionButton phoneanimateur;

//    public List<Animateur> ITEMS= new ArrayList<Animateur>();

    public AnimateursRecycler(Class<Animateur> modelClass, int modelLayout, Class<AnimateurViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot animateur: dataSnapshot.getChildren()) {
//                    // TODO: handle the post
//                    Animateur anim = animateur.getValue(Animateur.class);
//                    anim.setId(animateur.getKey());
//                    ITEMS.add(anim);
//                    Log.w(TAG, "Nom:Animateur : " + anim.getNom());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

//    public List<Animateur> getListeAnimateurs() {
//        return ITEMS;
//    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Animateur getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public DatabaseReference getRef(int position) {
        return super.getRef(position);
    }

    @Override
    public AnimateurViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mailanimateur = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailanimateur);
        mailanimateur.setVisibility(View.INVISIBLE);
        mailanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour envoyer un mail à " + animateurselectionne.getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        phoneanimateur = (FloatingActionButton) parent.getRootView().findViewById(R.id.phoneanimateur);
        phoneanimateur.setVisibility(View.INVISIBLE);
        phoneanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour téléphoner à " + animateurselectionne.getNom(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void populateViewHolder(AnimateurViewHolder viewHolder, final Animateur model, int position) {

        final DatabaseReference animateurRef = getRef(position);
        // Set click listener for the whole post view
        final String animateurKey = animateurRef.getKey();
        viewHolder.mListeEtabsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch PostDetailActivity
//                Log.d(TAG, "setOnClickListener:" + animateurKey + model.getNom());
                Intent intent = new Intent(v.getContext(), EtablissementsParAnimateurActivity.class);
                intent.putExtra(EtablissementsParAnimateurActivity.EXTRA_ANIMATEUR_KEY, animateurKey);
                v.getContext().startActivity(intent);
            }
        });
        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToAnimateur(model, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                // Need to write to both places the post is stored
                DatabaseReference globalAnimateurRef = mDatabase.child("animateurs").child(animateurKey);
                // Run two transactions
                onStarClicked(globalAnimateurRef);
            }
        });
    }

    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference etablissementRef) {
        etablissementRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Animateur p = mutableData.getValue(Animateur.class);
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
                animateurselectionne = dataSnapshot.getValue(Animateur.class);
                if (mailanimateur.getVisibility()==View.INVISIBLE) {
                    mailanimateur.setVisibility(View.VISIBLE);
                    phoneanimateur.setVisibility(View.VISIBLE);
                }
//                Log.d(TAG, "postTransaction:onComplete:" + dataSnapshot.getValue(Animateur.class).getNom());
                // Transaction completed
//                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]
}
