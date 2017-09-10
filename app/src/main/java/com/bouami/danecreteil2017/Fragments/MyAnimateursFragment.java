package com.bouami.danecreteil2017.Fragments;

import com.bouami.danecreteil2017.Models.Animateur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Animateur> getListeAnimateurs(DatabaseReference databaseReference) {
        final List<Animateur> ITEMS= new ArrayList<Animateur>();
        databaseReference.child("animateurs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot animateur: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Animateur anim = animateur.getValue(Animateur.class);
                    anim.setId(animateur.getKey());
                    ITEMS.add(anim);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return ITEMS;
    }

}
