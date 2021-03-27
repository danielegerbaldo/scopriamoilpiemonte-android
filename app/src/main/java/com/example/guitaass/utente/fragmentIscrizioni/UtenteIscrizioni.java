package com.example.guitaass.utente.fragmentIscrizioni;

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


public class UtenteIscrizioni extends android.app.Fragment {

    public UtenteIscrizioni() {
        // Required empty public constructor
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
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Utente> utenti = fakeRecyclerFill();
        if(utenti.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            messaggio.setVisibility(View.GONE);
            RecyclerAdapter adapter = new RecyclerAdapter(utenti);
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            messaggio.setVisibility(View.VISIBLE);
        }
    }

    private List<Utente> fakeRecyclerFill(){
        List<Utente> list = new ArrayList<>();
        /*list.add(new Utente(1,"Flavio", "Roman","status"));
        list.add(new Utente(2,"Mattia","Martello","status"));
        list.add(new Utente(3,"Daniele","Gerbaldo","status"));
        list.add(new Utente(4,"Francesco","Conforti","status"));*/
        return list;
    }
}