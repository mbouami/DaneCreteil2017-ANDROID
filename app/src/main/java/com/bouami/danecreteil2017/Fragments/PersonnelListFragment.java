package com.bouami.danecreteil2017.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017.Models.Animateur;
import com.bouami.danecreteil2017.Models.Etablissement;
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
 * Created by mbouami on 06/09/2017.
 */

public abstract class PersonnelListFragment extends Fragment {
    private static final String TAG = "EtablissementListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    private FirebaseRecyclerAdapter<Personnel,PersonnelViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FloatingActionButton mailpersonnel;
    private FloatingActionButton phonepersonnel;
    private Personnel personnelselectionne;


    public PersonnelListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_personnel,container,false);
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.personnel_list);
        mRecycler.setHasFixedSize(true);
        mailpersonnel = (FloatingActionButton) rootView.findViewById(R.id.mailpersonnel);
        mailpersonnel.setVisibility(View.INVISIBLE);
        mailpersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour envoyer un mail à " + personnelselectionne.getNom(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        phonepersonnel = (FloatingActionButton) rootView.findViewById(R.id.phonepersonnel);
        phonepersonnel.setVisibility(View.INVISIBLE);
        phonepersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour téléphoner à " + personnelselectionne.getNom(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        // Set up FirebaseRecyclerAdapter with the Query
        Query personnelQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Personnel, PersonnelViewHolder>(Personnel.class, R.layout.item_personnel,
                PersonnelViewHolder.class, personnelQuery) {
            @Override
            protected void populateViewHolder(final PersonnelViewHolder viewHolder, final Personnel model, final int position) {
                final DatabaseReference personnelRef = getRef(position);

                // Set click listener for the whole post view
                final String personnelKey = personnelRef.getKey();

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPersonnel(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalAnimateurRef = mDatabase.child("personnel").child(personnelKey);
//                        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());
//                        Query etab = getQueryEtablissement(mDatabase,personnelKey);
//                        DatabaseReference globalEtablissementRef =  mDatabase.child("etablissements").orderByChild("personnel/"+personnelRef.getKey()).equalTo(true).getRef();
                        // Run two transactions
                        onStarClicked(globalAnimateurRef);
//                        onStarClickedEtab(globalEtablissementRef);
//                        onStarClicked(userPostRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
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
                    mailpersonnel.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "postTransaction:onComplete:" + dataSnapshot.getValue(Personnel.class).getNom() + " "+ personnelRef.getKey());
                // Transaction completed
//                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]

    // [START post_stars_transaction]
//    private void onStarClickedEtab(final DatabaseReference etabRef) {
//        etabRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Etablissement p = mutableData.getValue(Etablissement.class);
//                if (p == null) {
//                    return Transaction.success(mutableData);
//                }
//                // Set value and report transaction success
//                mutableData.setValue(p);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b,
//                                   DataSnapshot dataSnapshot) {
//                Log.d(TAG, "postTransaction:onComplete:" + dataSnapshot.getValue(Etablissement.class).getNom() + " "+ etabRef.getKey());
//                // Transaction completed
////                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
//            }
//        });
//    }
    // [END post_stars_transaction]


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
    public abstract Query getQuery(DatabaseReference databaseReference);
//    public abstract Query getQueryEtablissement(DatabaseReference databaseReference, String personnelkey);

}
