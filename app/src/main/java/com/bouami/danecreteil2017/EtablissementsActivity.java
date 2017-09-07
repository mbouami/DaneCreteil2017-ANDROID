package com.bouami.danecreteil2017;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bouami.danecreteil2017.Fragments.AnimateursFragment;
import com.bouami.danecreteil2017.Fragments.EtablissementsFragment;
import com.bouami.danecreteil2017.Fragments.dummy.AnimateursContent;
import com.bouami.danecreteil2017.Fragments.dummy.DummyContent;
import com.bouami.danecreteil2017.Fragments.dummy.EtablissementsContent;
import com.bouami.danecreteil2017.Models.AnimateurModel;
import com.bouami.danecreteil2017.Models.Etablissement;
import com.bouami.danecreteil2017.Models.EtablissementModel;

import java.util.ArrayList;
import java.util.List;

public class EtablissementsActivity extends AppCompatActivity implements EtablissementsFragment.OnListFragmentInteractionListener {
    private final String EtablissementsActivity_TAG = "EtablissementsActivity";
    public static List<Etablissement> listesetablissements = new ArrayList<Etablissement>();
    public static String idanimateur;
    private static final String ID_ANIMATEUR = "idanimateur";
    private static View vueencours = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etablissements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaretablissement);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            idanimateur = getIntent().getStringExtra("idanimateur");
//            new getListeDesEtablissements().execute(idanimateur,null,null);
            Fragment newFragment = new EtablissementsFragment();
            Bundle args = new Bundle();
            args.putString(ID_ANIMATEUR, idanimateur);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onListFragmentInteraction(Etablissement item) {
//        Log.w(EtablissementsActivity_TAG, "Etablissement  : "+ item.getNom());
        Snackbar.make(vueencours, "Etablissement  : "+ item.getNom(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        vueencours = parent;
        return super.onCreateView(parent, name, context, attrs);
    }

/*    private class getListeDesEtablissements extends AsyncTask<String, Void, String> {
        private EtablissementsContent etabcontent;
//        private List<EtablissementModel> listesetablissementsparanimateur = new ArrayList<EtablissementModel>();
        @Override
        protected String doInBackground(String... params) {
            etabcontent= new EtablissementsContent(params[0]);
            listesetablissements = etabcontent.getITEMS();
            Log.w(EtablissementsActivity_TAG, "ID Animateur doInBackground  : "+ params[0]+ "---"+listesetablissements.size());
            return null;
        }

*//*        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listesetablissementsparanimateur = listesetablissements;
            etabcontent= new EtablissementsContent(idanimateur);
*//**//*            listesetablissements = etabcontent.getITEMS();
            for (int i=0;i<listesetablissementsparanimateur.size();i++) {
                listesetablissements.remove(listesetablissementsparanimateur.get(i));
            }*//**//*
            Log.w(EtablissementsActivity_TAG, "ID Animateur onPreExecute listesetablissements  : "+ idanimateur+ "---"+listesetablissements.size());
        }*//*

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
*//*            for (int i=0;i<listesetablissementsparanimateur.size();i++) {
                listesetablissements.remove(listesetablissementsparanimateur.get(i));
            }*//*
            Log.w(EtablissementsActivity_TAG, "ID listesetablissements onPostExecute  : "+ idanimateur+ "---"+listesetablissements.size());
            Fragment newFragment = new EtablissementsFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }*/
}
