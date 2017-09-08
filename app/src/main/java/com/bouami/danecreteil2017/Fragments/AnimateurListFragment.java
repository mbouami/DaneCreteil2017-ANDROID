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
                Query animateurssearchQuery = getQuerySearchByNom(mDatabase,newText);
                mAdapter = new AnimateursRecycler(Animateur.class,R.layout.item_animateur,AnimateurViewHolder.class,animateurssearchQuery);
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
        mRecycler = (RecyclerView) rootView.findViewById(R.id.animateur_list);
        mRecycler.setHasFixedSize(true);
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
        Query animateursQuery = getQuery(mDatabase);
        mAdapter = new AnimateursRecycler(Animateur.class,R.layout.item_animateur,AnimateurViewHolder.class,animateursQuery);
        mRecycler.setAdapter(mAdapter);
    }

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
