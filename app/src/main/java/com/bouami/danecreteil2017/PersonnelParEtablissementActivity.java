package com.bouami.danecreteil2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.bouami.danecreteil2017.Fragments.PersonnelRecycler;
import com.bouami.danecreteil2017.Models.Etablissement;
import com.bouami.danecreteil2017.Models.Personnel;
import com.bouami.danecreteil2017.viewholder.PersonnelViewHolder;
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
 * Created by mbouami on 10/09/2017.
 */

public class PersonnelParEtablissementActivity extends AppCompatActivity {

    private static final String TAG = "PersonnelParEtablissementActivity";
    public static final String EXTRA_ETABLISSEMENT_KEY = "etablissement_key";
    private String mEtablissementKey;
    private static DatabaseReference mDatabase;
    private static ValueEventListener postListener;
    private static final List<Personnel> ITEMS= new ArrayList<Personnel>();
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Personnel,PersonnelViewHolder> mAdapter;
    private DatabaseReference mEtablissementReference;
    private TextView nometab;
    private TextView teletab;
    private TextView faxetab;
    private TextView mailetab;
    private TextView adresseetab;
    private TextView villeetab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_par_etab);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nometab = (TextView) this.findViewById(R.id.nom);
        teletab = (TextView) this.findViewById(R.id.tel);
        faxetab = (TextView) this.findViewById(R.id.fax);
        mailetab = (TextView) this.findViewById(R.id.email);
        adresseetab = (TextView) this.findViewById(R.id.adresse);
        villeetab = (TextView) this.findViewById(R.id.ville);
        recyclerView = (RecyclerView) this.findViewById(R.id.recycler_personnel_par_etab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get post key from intent
        mEtablissementKey = getIntent().getStringExtra(EXTRA_ETABLISSEMENT_KEY);
        if (mEtablissementKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ETABLISSEMENT_KEY");
        }
        Query personnelparetabQuery = getQueryPersonnelParEtab(mDatabase,mEtablissementKey);
        mAdapter = new PersonnelRecycler(Personnel.class,R.layout.item_personnel,PersonnelViewHolder.class,personnelparetabQuery);
        recyclerView.setAdapter(mAdapter);
    }

    public Query getQueryPersonnelParEtab(DatabaseReference databaseReference, String etabkey) {
        return databaseReference.child("personnel").orderByChild("etablissement/"+etabkey).equalTo(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEtablissementReference = mDatabase.child("etablissements").child(mEtablissementKey);
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Etablissement etablissement = dataSnapshot.getValue(Etablissement.class);
                nometab.setText(etablissement.getType()+ " "+etablissement.getNom());
                teletab.setText("Tél : "+etablissement.getTel());
                faxetab.setText("Fax : "+etablissement.getFax());
                mailetab.setText(etablissement.getEmail());
                adresseetab.setText(etablissement.getAdresse());
                villeetab.setText(etablissement.getCp()+ " "+etablissement.getVille());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mEtablissementReference.addListenerForSingleValueEvent(postListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
