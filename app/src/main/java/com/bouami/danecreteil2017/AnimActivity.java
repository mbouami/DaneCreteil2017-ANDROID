package com.bouami.danecreteil2017;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bouami.danecreteil2017.Fragments.MyAnimateursFragment;
import com.bouami.danecreteil2017.Fragments.MyEtablissementsFragment;

/**
 * Created by mbouami on 02/09/2017.
 */

public class AnimActivity extends AppCompatActivity {
    private static final String TAG = "DaneCréteilCloud AnimActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
/*    public FloatingActionButton mailanimateur;
    public FloatingActionButton phoneanimateur;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        Log.d(TAG, "AccueilActivity:");
        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new MyAnimateursFragment(),
                    new MyEtablissementsFragment(),
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_my_animateurs),
                    getString(R.string.heading_my_etablissements)
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaranimateur);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

/*        mailanimateur = (FloatingActionButton) findViewById(R.id.mail);
        mailanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour envoyer un mail à ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        phoneanimateur = (FloatingActionButton) findViewById(R.id.phone);
        phoneanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prévoire une action pour téléphoner à", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        enabledisableMailFloatingActionButton(View.INVISIBLE);
        enabledisablePhoneFloatingActionButton(View.INVISIBLE);*/
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animateurs, menu);


        MenuItem menusearch = menu.findItem(R.id.animateurrechercher);
        SearchView searchview = (SearchView) menusearch.getActionView();
//        searchView.setIconifiedByDefault(false);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener (){

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "setOnQueryTextListener: onQueryTextSubmit " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "setOnQueryTextListener: onQueryTextChange " + newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout :
                Log.d(TAG, "AccueilActivity: action_logout activé");
                return true;
            case R.id.animateurrechercher :
                Log.d(TAG, "AccueilActivity: animateurrechercher activé");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*    public void enabledisableMailFloatingActionButton(Integer etat) {
//        mailanimateur.setEnabled(etat);
        mailanimateur.setVisibility(etat);
    }

    public void enabledisablePhoneFloatingActionButton(Integer etat) {
//        phoneanimateur.setEnabled(etat);
        phoneanimateur.setVisibility(etat);
    }*/
}
