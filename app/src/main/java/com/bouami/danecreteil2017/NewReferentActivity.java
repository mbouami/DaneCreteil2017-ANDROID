package com.bouami.danecreteil2017;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bouami.danecreteil2017.Models.Referent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewReferentActivity extends AppCompatActivity {

    private static final String TAG = "NewReferentActivity";
    private static final String REQUIRED = "Required";
    public static final String EXTRA_ETABLISSEMENT_KEY = "etablissement_key";
    private String mEtablissementKey;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private RadioGroup mGenreReferent;
    private EditText mNomReferent;
    private EditText mPrenomReferent;
    private EditText mTelReferent;
    private EditText mMailReferent;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_referent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEtablissementKey = getIntent().getStringExtra(EXTRA_ETABLISSEMENT_KEY);
        if (mEtablissementKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ETABLISSEMENT_KEY");
        }
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_add_new_referent);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReferent();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mGenreReferent = (RadioGroup) findViewById(R.id.genre);
        mNomReferent = (EditText) findViewById(R.id.nom);
        mPrenomReferent = (EditText) findViewById(R.id.prenom);
        mTelReferent = (EditText) findViewById(R.id.tel);
        mMailReferent = (EditText) findViewById(R.id.email);
    }


    private void submitReferent() {
        final String genre = ((RadioButton) findViewById(mGenreReferent.getCheckedRadioButtonId())).getText().toString();
        final String nom = mNomReferent.getText().toString();
        final String prenom = mPrenomReferent.getText().toString();
        final String tel = mTelReferent.getText().toString();
        final String email = mMailReferent.getText().toString();

        if (TextUtils.isEmpty(nom)) {
            mNomReferent.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(prenom)) {
            mPrenomReferent.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();
        writeNewReferent(new Referent(genre,nom,prenom,tel,email));
//        // [START single_value_read]
//        final String userId = getUid();
//        mDatabase.child("referents").child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//                        Referent referent = dataSnapshot.getValue(Referent.class);
//
//                        // [START_EXCLUDE]
//                        if (user == null) {
//                            // User is null, error out
//                            Log.e(TAG, "User " + userId + " is unexpectedly null");
//                            Toast.makeText(NewPostActivity.this,
//                                    "Error: could not fetch user.",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Write new post
//                            writeNewPost(userId, user.username, title, body);
//                        }
//
//                        // Finish this Activity, back to the stream
//                        setEditingEnabled(true);
//                        finish();
//                        // [END_EXCLUDE]
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                        // [START_EXCLUDE]
//                        setEditingEnabled(true);
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mNomReferent.setEnabled(enabled);
        mPrenomReferent.setEnabled(enabled);
        mTelReferent.setEnabled(enabled);
        mMailReferent.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewReferent(Referent referent) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("referents").push().getKey();
        Map<String, Object> postValues = referent.toMap(mEtablissementKey);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/personnel/" + key, postValues);
//        childUpdates.put("/referents/" + key + "/etablissements", postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]

}
