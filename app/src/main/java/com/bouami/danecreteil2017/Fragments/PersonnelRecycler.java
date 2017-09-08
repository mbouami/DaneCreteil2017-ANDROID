package com.bouami.danecreteil2017.Fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017.Models.Animateur;
import com.bouami.danecreteil2017.Models.Personnel;
import com.bouami.danecreteil2017.R;
import com.bouami.danecreteil2017.viewholder.PersonnelViewHolder;
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

public class PersonnelRecycler extends FirebaseRecyclerAdapter<Personnel,PersonnelViewHolder> {
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
    private static final String TAG = "PersonnelRecycler";
    private DatabaseReference mDatabase;
    private FloatingActionButton mailpersonnel;
    private FloatingActionButton phonepersonnel;
    private Personnel personnelselectionne;

    public PersonnelRecycler(Class<Personnel> modelClass, int modelLayout, Class<PersonnelViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public PersonnelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mailpersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailpersonnel);
        mailpersonnel.setVisibility(View.INVISIBLE);
        mailpersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour envoyer un mail à " + personnelselectionne.getNom(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        phonepersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.phonepersonnel);
        phonepersonnel.setVisibility(View.INVISIBLE);
        phonepersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour téléphoner à " + personnelselectionne.getNom(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void populateViewHolder(PersonnelViewHolder viewHolder, Personnel model, int position) {
        final DatabaseReference personnelRef = getRef(position);

        // Set click listener for the whole post view
        final String personnelKey = personnelRef.getKey();

        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToPersonnel(model, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                // Need to write to both places the post is stored
                DatabaseReference globalpersonnelRef = mDatabase.child("personnel").child(personnelKey);
                onStarClicked(globalpersonnelRef);
            }
        });
    }

    // [START post_stars_transaction]
    private void onStarClicked(final DatabaseReference personnelRef) {
        personnelRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Animateur p = mutableData.getValue(Animateur.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }
                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                personnelselectionne = dataSnapshot.getValue(Personnel.class);
                if (mailpersonnel.getVisibility()==View.INVISIBLE) {
                    mailpersonnel.setVisibility(View.VISIBLE);
                    phonepersonnel.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "postTransaction:onComplete:" + dataSnapshot.getValue(Personnel.class).getNom() + " "+ personnelRef.getKey());
                // Transaction completed
//                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]
}
