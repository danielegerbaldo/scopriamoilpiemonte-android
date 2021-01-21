package com.example.guitaass.sindaco.fragmentMioComune.fragmentElencoPersone;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentElencoPersone extends Fragment {
    List<Utente> utenti;

    @SuppressLint("ValidFragment")
    public FragmentElencoPersone(List<Utente> utenti) {
        this.utenti = utenti;
    }

    public FragmentElencoPersone() {
        utenti = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View view = getView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sindaco_elenco_persone, container, false);
        initRecyler(view);
        return view;
    }

    private void initRecyler(View view){
        TextView messaggio = view.findViewById(R.id.no_persone);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if(utenti.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            messaggio.setVisibility(View.GONE);
            PersoneRecyclerAdapter adapter = new PersoneRecyclerAdapter(utenti);
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            messaggio.setVisibility(View.VISIBLE);
        }
    }

}
