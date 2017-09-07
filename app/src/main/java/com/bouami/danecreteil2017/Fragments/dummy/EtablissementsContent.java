package com.bouami.danecreteil2017.Fragments.dummy;

import android.util.Log;

import com.bouami.danecreteil2017.EtablissementsActivity;
import com.bouami.danecreteil2017.Models.AnimateurModel;
import com.bouami.danecreteil2017.Models.Etablissement;
import com.bouami.danecreteil2017.Models.EtablissementModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mbouami on 24/08/2017.
 */

public class EtablissementsContent {
    public static DatabaseReference mDatabase = null;
    public static ValueEventListener postListener = null;
    public static final String TAG = "EtablissementsContent";
    public static final List<Etablissement> ITEMS= new ArrayList<Etablissement>();
    public static final Map<String,Etablissement> ITEM_MAP = new HashMap<String,Etablissement>();


    private static String idanimateur = "";

    public List<Etablissement> getITEMS() {
        return ITEMS;
    }

    public EtablissementsContent() {
    }

    public EtablissementsContent(String idanim) {
        ITEMS.clear();
        createListeEtablissements(idanim);
    }

    public static void setIdanimateur(String id) {
        EtablissementsContent.idanimateur = id;
    }

    public static String getIdanimateur() {
        return idanimateur;
    }


    public static void createListeEtablissements(String idanim) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot etablissement: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Etablissement etab = etablissement.getValue(Etablissement.class);
                    addItem(etab);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

/*       ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                EtablissementModel etab = dataSnapshot.getValue(EtablissementModel.class);
                addItem(etab);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("etablissements").orderByChild("animateurs/"+idanim).equalTo(true).addChildEventListener(childEventListener);*/
//        mDatabase.child("etablissements").orderByChild("animateurs/"+idanim).equalTo(true).addValueEventListener(postListener);
        mDatabase.child("etablissements").orderByChild("animateurs/"+idanim).equalTo(true).addListenerForSingleValueEvent(postListener);
    }


    private static void addItem(Etablissement item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(ITEMS.size()),item);
//        Log.w(TAG, "Etablissement item : "+ String.valueOf(ITEMS.size())+ "---" +item.getNom()+ " "+ item.getVille());
    }

}
