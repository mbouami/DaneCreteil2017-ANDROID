package com.bouami.danecreteil2017.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017.AnimActivity;
import com.bouami.danecreteil2017.EtablissementsParAnimateurActivity;
import com.bouami.danecreteil2017.R;
import com.bouami.danecreteil2017.Models.Animateur;
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
 * Created by mbouami on 02/09/2017.
 */

public abstract class AnimateurListFragment extends Fragment {
    private static final String TAG = "EtablissementListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    private FirebaseRecyclerAdapter<Animateur,AnimateurViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FloatingActionButton mailanimateur;
    private FloatingActionButton phoneanimateur;
    private Animateur animateurselectionne;


    public AnimateurListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menusearch = menu.findItem(R.id.animateurrechercher);
        SearchView searchview = (SearchView) menusearch.getActionView();
//        searchView.setIconifiedByDefault(false);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener (){

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "setOnQueryTextListener: onQueryTextSubmit " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "setOnQueryTextListener: onQueryTextChange " + newText);
                Query animateursQuery = getQuerySearchByNom(mDatabase,newText);
                mAdapter = new FirebaseRecyclerAdapter<Animateur, AnimateurViewHolder>(Animateur.class, R.layout.item_animateur,
                        AnimateurViewHolder.class, animateursQuery) {
                    @Override
                    protected void populateViewHolder(final AnimateurViewHolder viewHolder, final Animateur model, final int position) {
                        final DatabaseReference animateurRef = getRef(position);

                        // Set click listener for the whole post view
                        final String animateurKey = animateurRef.getKey();
                        viewHolder.mPhotoView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Launch PostDetailActivity
                                Log.d(TAG, "setOnClickListener:" + animateurKey + model.getNom());
                                Intent intent = new Intent(getActivity(), EtablissementsParAnimateurActivity.class);
                                intent.putExtra(EtablissementsParAnimateurActivity.EXTRA_ANIMATEUR_KEY, animateurKey);
                                startActivity(intent);
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
                };
                mRecycler.setAdapter(mAdapter);
                return false;
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_animateurs,container,false);
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.animateur_list);
        mRecycler.setHasFixedSize(true);
        mailanimateur = (FloatingActionButton) rootView.findViewById(R.id.mailanimateur);
        mailanimateur.setVisibility(View.INVISIBLE);
        mailanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour envoyer un mail à " + animateurselectionne.getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        phoneanimateur = (FloatingActionButton) rootView.findViewById(R.id.phoneanimateur);
        phoneanimateur.setVisibility(View.INVISIBLE);
        phoneanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour téléphoner à " + animateurselectionne.getNom(), Snackbar.LENGTH_LONG)
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
        Query animateursQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Animateur, AnimateurViewHolder>(Animateur.class, R.layout.item_animateur,
                AnimateurViewHolder.class, animateursQuery) {
            @Override
            protected void populateViewHolder(final AnimateurViewHolder viewHolder, final Animateur model, final int position) {
                final DatabaseReference animateurRef = getRef(position);

                // Set click listener for the whole post view
                final String animateurKey = animateurRef.getKey();
                viewHolder.mPhotoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Log.d(TAG, "setOnClickListener:" + animateurKey + model.getNom());
                        Intent intent = new Intent(getActivity(), EtablissementsParAnimateurActivity.class);
                        intent.putExtra(EtablissementsParAnimateurActivity.EXTRA_ANIMATEUR_KEY, animateurKey);
                        startActivity(intent);
//                        Log.d(TAG, "setOnClickListener:" + animateurKey + model.getNom());
//                        animateurselectionne = model;
//                        if (mailanimateur.getVisibility()==View.INVISIBLE) {
//                            mailanimateur.setVisibility(View.VISIBLE);
//                            phoneanimateur.setVisibility(View.VISIBLE);
//                        }
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
/*                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }*/

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToAnimateur(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalAnimateurRef = mDatabase.child("animateurs").child(animateurRef.getKey());
//                        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                        onStarClicked(globalAnimateurRef);
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
                Log.d(TAG, "postTransaction:onComplete:" + dataSnapshot.getValue(Animateur.class).getNom());
                // Transaction completed
//                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
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
    public abstract Query getQuerySearchByNom(DatabaseReference databaseReference, String search);
}
