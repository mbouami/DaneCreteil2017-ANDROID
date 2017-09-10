package com.bouami.danecreteil2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.bouami.danecreteil2017.Fragments.EtablissementsRecycler;
import com.bouami.danecreteil2017.Models.Animateur;
import com.bouami.danecreteil2017.Models.Etablissement;
import com.bouami.danecreteil2017.viewholder.EtablissementViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbouami on 06/09/2017.
 */

public class EtablissementsParAnimateurActivity extends AppCompatActivity {

    private static final String TAG = "EtablissementsParAnimateurActivity";
    public static final String EXTRA_ANIMATEUR_KEY = "animateur_key";
    private String mAnimateurKey;
    private static DatabaseReference mDatabase;
    private static ValueEventListener postListener;
    private static final List<Etablissement> ITEMS= new ArrayList<Etablissement>();
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Etablissement,EtablissementViewHolder> mAdapter;
    private DatabaseReference mAnimateurReference;
    private TextView nomanimateur;
    private TextView telanimateur;
    private TextView mailanimateur;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etabs_par_animateur);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        nomanimateur = (TextView) this.findViewById(R.id.nom);
        telanimateur = (TextView) this.findViewById(R.id.tel);
        mailanimateur = (TextView) this.findViewById(R.id.email);
        recyclerView = (RecyclerView) this.findViewById(R.id.recycler_etabs_par_anim);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get post key from intent
        mAnimateurKey = getIntent().getStringExtra(EXTRA_ANIMATEUR_KEY);
        if (mAnimateurKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ANIMATEUR_KEY");
        }
        Query etabsparanimateurQuery = getQueryEtabsParAnim(mDatabase,mAnimateurKey);
        mAdapter = new EtablissementsRecycler(Etablissement.class,R.layout.item_etablissement,EtablissementViewHolder.class,etabsparanimateurQuery);
        recyclerView.setAdapter(mAdapter);

    }

    public Query getQueryEtabsParAnim(DatabaseReference databaseReference, String animkey) {
        return databaseReference.child("etablissements").orderByChild("animateurs/"+animkey).equalTo(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAnimateurReference = FirebaseDatabase.getInstance().getReference().child("animateurs").child(mAnimateurKey);
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Animateur animateur = dataSnapshot.getValue(Animateur.class);
                nomanimateur.setText(animateur.getGenre()+ " "+animateur.getPrenom()+ " "+animateur.getNom());
                telanimateur.setText(animateur.getTel());
                mailanimateur.setText(animateur.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mAnimateurReference.addListenerForSingleValueEvent(postListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animateurs, menu);
        return true;
    }
}
