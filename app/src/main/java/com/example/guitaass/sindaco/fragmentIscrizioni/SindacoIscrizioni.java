package com.example.guitaass.sindaco.fragmentIscrizioni;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.R;

import java.util.ArrayList;
import java.util.List;


public class SindacoIscrizioni extends android.app.Fragment {

    public SindacoIscrizioni() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sindaco_iscrizioni, container, false);
        initRecyler(view);
        return view;
    }

    private void initRecyler(View view){
        //TODO: per ora semplice con il caso banale, è poi da rendere più complesso
        TextView messaggio = view.findViewById(R.id.messaggio);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Evento> eventi = fakeRecyclerFill();
        if(eventi.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            messaggio.setVisibility(View.GONE);
            RecyclerAdapter adapter = new RecyclerAdapter(eventi);
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            messaggio.setVisibility(View.VISIBLE);
        }
    }

    private List<Evento> fakeRecyclerFill(){
        List<Evento> list = new ArrayList<>();
        list.add(new Evento((long)1, "prova", 10, 2,
                true, "evento di prova fake per verificare il corretto funzionamento dell'app",
                "occhio a u coviddi", null, null, 1, 1));
        list.add(new Evento((long)2, "festa delle ciule piene", 100, 2,
                false, "evento tipico di Milanere, sono svariate le edizioni di questo evento che ricorre da più di 50 anni dove i protagonisti sono sempre stati: produttori locali, bande e scuole. Punto di forza? Le ciule ripiene e le frittelle di mele!!!!",
                "non adatto a chi non gradisce i prodotti piemontesi, neh?!", null, null, 2, 2));
        return list;
    }
}