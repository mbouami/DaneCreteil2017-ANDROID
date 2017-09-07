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

import com.bouami.danecreteil2017.R;
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
 * Created by mbouami on 02/09/2017.
 */

public abstract class EtablissementListFragment extends Fragment {
    private static final String TAG = "EtablissementListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    private FirebaseRecyclerAdapter<Etablissement,EtablissementViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FloatingActionButton mailetablissement;
    private FloatingActionButton phoneetablissement;
    private Etablissement etablissementselectionne;


    public EtablissementListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_etablissements,container,false);
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.etablissement_list);
        mRecycler.setHasFixedSize(true);
        mailetablissement = (FloatingActionButton) rootView.findViewById(R.id.mailetablissement);
        mailetablissement.setVisibility(View.INVISIBLE);
        mailetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour envoyer un mail à " + etablissementselectionne.getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        phoneetablissement = (FloatingActionButton) rootView.findViewById(R.id.phoneetablissement);
        phoneetablissement.setVisibility(View.INVISIBLE);
        phoneetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour téléphoner à " + etablissementselectionne.getNom(), Snackbar.LENGTH_LONG)
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
        Query etablissementsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Etablissement, EtablissementViewHolder>(Etablissement.class, R.layout.item_etablissement,
                EtablissementViewHolder.class, etablissementsQuery) {
            @Override
            protected void populateViewHolder(final EtablissementViewHolder viewHolder, final Etablissement model, final int position) {
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
//                        DatabaseReference globalEtablissementRef = mDatabase.child("posts").child(etablissementRef.getKey());
                        DatabaseReference globalEtablissementRef = mDatabase.child("etablissements").child(etablissementRef.getKey());
//                        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                        onStarClicked(globalEtablissementRef);
//                        onStarClicked(userPostRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
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
                    mailetablissement.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}
