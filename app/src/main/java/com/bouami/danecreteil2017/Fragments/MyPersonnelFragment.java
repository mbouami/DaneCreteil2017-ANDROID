package com.bouami.danecreteil2017.Fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by mbouami on 06/09/2017.
 */

public class MyPersonnelFragment extends PersonnelListFragment {

    public MyPersonnelFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("personnel");
    }

//    @Override
//    public Query getQueryEtablissement(DatabaseReference databaseReference, String personnelkey) {
//        Query etab = databaseReference.child("etablissements").orderByChild("personnel/"+personnelkey).equalTo(true);
//        return etab;
//    }

}
