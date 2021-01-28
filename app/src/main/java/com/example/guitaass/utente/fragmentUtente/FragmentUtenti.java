package com.example.guitaass.utente.fragmentUtente;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentUtenti extends android.app.Fragment {

    private List<Utente> fakeList;
    private boolean visibilitaBottoniBassi;

    public FragmentUtenti() {
        fakeList = new ArrayList<>();
    }

    @SuppressLint("ValidFragment")
    public FragmentUtenti(List<Utente> fakeList) {
        this.fakeList = fakeList;
        visibilitaBottoniBassi = false;
    }

    @SuppressLint("ValidFragment")
    public FragmentUtenti(List<Utente> fakeList, boolean visibilitaBottoniBassi) {
        this.fakeList = fakeList;
        this.visibilitaBottoniBassi = visibilitaBottoniBassi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utente_iscrizioni, container, false);
        initRecyler(view);
        return view;
    }

    private void initRecyler(View view){
        //TODO: per ora semplice con il caso banale, è poi da rendere più complesso
        TextView messaggio = view.findViewById(R.id.messaggio_utente);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_utente);
        LinearLayout bottomLayout = view.findViewById(R.id.bottom_layout_utente);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Utente> utenti = fakeList;
        if(visibilitaBottoniBassi){
            bottomLayout.setVisibility(View.VISIBLE);
        }else{
            bottomLayout.setVisibility(View.GONE);
        }

        if(utenti.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            messaggio.setVisibility(View.GONE);
            FragmentUtentiRecyclerAdapter adapter = new FragmentUtentiRecyclerAdapter(utenti);
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            messaggio.setVisibility(View.VISIBLE);
        }
    }

}

