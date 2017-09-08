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

    @Override
    public Query getQuerySearchByNom(DatabaseReference databaseReference, String search) {
        String elementrecherche = search.toUpperCase();
        return databaseReference.child("animateurs").orderByChild("nom").startAt(elementrecherche).endAt(elementrecherche+"\uf8ff");
    }

}
