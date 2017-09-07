package com.bouami.danecreteil2017.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;

import com.bouami.danecreteil2017.AnimateursActivity;
import com.bouami.danecreteil2017.Fragments.dummy.AnimateursContent;
import com.bouami.danecreteil2017.Models.Animateur;
import com.bouami.danecreteil2017.Models.AnimateurModel;
import com.bouami.danecreteil2017.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AnimateursFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private final String AnimateursFragment_TAG = "AnimateursFragment";
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnimateursFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AnimateursFragment newInstance(int columnCount) {
        AnimateursFragment fragment = new AnimateursFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animateurs_list, container, false);

        // Set the adapter
//        if (view instanceof RecyclerView) {
            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView = (RecyclerView) view.findViewById(R.id.listanimateurs);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyAnimateursRecyclerViewAdapter(AnimateursContent.ITEMS, mListener));
//        }
        SearchView searchanimateur = (SearchView) view.findViewById(R.id.searchViewAnimateur);
        searchanimateur.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.w(AnimateursFragment_TAG, "onQueryTextSubmit  : "+ query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length()==0) {
                    recyclerView.setAdapter(new MyAnimateursRecyclerViewAdapter(AnimateursContent.ITEMS, mListener));
                } else {
                    List<Animateur> listesesanimateursfiltre= new ArrayList<Animateur>();
                    for (int i = 0; i < AnimateursContent.ITEMS.size(); i++)
                    {
                        String nomanimateur = AnimateursContent.ITEMS.get(i).getNom().toLowerCase();
                        String prenomanimateur = AnimateursContent.ITEMS.get(i).getPrenom().toLowerCase();
                        if(nomanimateur.contains(newText.toLowerCase()) || prenomanimateur.contains(newText.toLowerCase())) {
                            listesesanimateursfiltre.add(AnimateursContent.ITEMS.get(i));
                        }
                    }
                    recyclerView.setAdapter(new MyAnimateursRecyclerViewAdapter(listesesanimateursfiltre, mListener));
                }
                return false;
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Animateur item);
    }
}
