package com.bouami.danecreteil2017.Fragments.dummy;

import android.util.Log;

import com.bouami.danecreteil2017.Models.Animateur;
import com.bouami.danecreteil2017.Models.AnimateurModel;
import com.bouami.danecreteil2017.Models.EtablissementModel;
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
 * Created by mbouami on 20/08/2017.
 */

public class AnimateursContent {

    public static DatabaseReference mDatabase = null;
    public static ValueEventListener postListener = null;
    public static final String TAG = "AnimateursContent";
    public static final List<Animateur> ITEMS= new ArrayList<Animateur>();
    public static final Map<String,Animateur> ITEM_MAP = new HashMap<String,Animateur>();
    static {
//        Log.w(TAG, "AnimateursContent  : ");
        createListeAnimateurs();
    }
/*    public AnimateursContent() {
        Log.w(TAG, "AnimateursContent  : ");
        createListeAnimateurs();
    }*/

    private static void createListeAnimateurs() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot animateur: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Animateur anim = animateur.getValue(Animateur.class);
                    anim.setId(animateur.getKey());
                    addItem(anim);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
//        mDatabase.child("animateurs").addValueEventListener(postListener);
        mDatabase.child("animateurs").addListenerForSingleValueEvent(postListener);
    }


    private static void addItem(Animateur item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(ITEMS.size()),item);
//        Log.w(TAG, "Animateur item : "+ String.valueOf(ITEMS.size())+ "---" + item.getId()+ "---" + item.getNom()+ " "+ item.getPrenom().toLowerCase());
    }


}
