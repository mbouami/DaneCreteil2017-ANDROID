package com.bouami.danecreteil2017;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.bouami.danecreteil2017.Fragments.AnimateursFragment;
import com.bouami.danecreteil2017.Models.Animateur;
import com.bouami.danecreteil2017.Models.AnimateurModel;

import java.util.ArrayList;
import java.util.List;

public class AnimateursActivity extends AppCompatActivity implements AnimateursFragment.OnListFragmentInteractionListener, SearchView.OnQueryTextListener  {
    private final String ANIMATEURSFRAGMENT_TAG = "LISTE_DES_ANIMATEURS";
    public static List<Animateur> listesesanimateurs= new ArrayList<Animateur>();
    private SearchManager searchmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animateurs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaranimateur);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (savedInstanceState == null) {
            Fragment newFragment = new AnimateursFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflate = getMenuInflater();
        menuinflate.inflate(R.menu.menu_animateurs,menu);
//        MenuItem searchItem = menu.findItem(R.id.animateurrechercher);
//        SearchView searchview = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchview.setSearchableInfo(searchmanager.getSearchableInfo(getComponentName()));
/*        searchview.setIconifiedByDefault(false);*/
//        searchview.setOnQueryTextListener(this);
/*        searchview.setOnCloseListener(this);*/
//        searchview.requestFocus();
//        searchview.setOnQueryTextListener(this);
/*        SearchView searchview = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.w(ANIMATEURSFRAGMENT_TAG, "Animateur  : "+ newText);
                return false;
            }
        });*//*
        return super.onCreateOptionsMenu(menu);*/
        return true;
    }

    @Override
    public void onListFragmentInteraction(Animateur item) {
//        Log.w(ANIMATEURSFRAGMENT_TAG, "Animateur  : "+ item.getNom());
        Intent intent = new Intent(this, EtablissementsActivity.class).putExtra("idanimateur",item.getId());
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.w(ANIMATEURSFRAGMENT_TAG, "Animateur  : "+ query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
