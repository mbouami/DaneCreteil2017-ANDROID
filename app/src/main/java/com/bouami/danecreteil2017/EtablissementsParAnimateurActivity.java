package com.bouami.danecreteil2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by mbouami on 06/09/2017.
 */

public class EtablissementsParAnimateurActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EtablissementsParAnimateurActivity";
    public static final String EXTRA_ANIMATEUR_KEY = "animateur_key";
    private String mAnimateurKey;
    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etabs_par_animateur);
        // Get post key from intent
        mAnimateurKey = getIntent().getStringExtra(EXTRA_ANIMATEUR_KEY);
        if (mAnimateurKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ANIMATEUR_KEY");
        }
        Log.d(TAG, "onCreate:" + mAnimateurKey);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
