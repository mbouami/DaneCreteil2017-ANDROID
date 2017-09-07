package com.bouami.danecreteil2017.Fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by mbouami on 02/09/2017.
 */

public class MyAnimateursFragment extends AnimateurListFragment {

    public MyAnimateursFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("animateurs");
    }
}
