package com.example.guitaass.condivisi.fragmentEventi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentEventi extends android.app.Fragment {

    private List<Evento> fakeList;
    private boolean visibilitaBottoniBassi;

    public FragmentEventi() {
        fakeList = new ArrayList<>();
    }

    @SuppressLint("ValidFragment")
    public FragmentEventi(List<Evento> fakeList) {
        this.fakeList = fakeList;
        visibilitaBottoniBassi = false;
    }

    @SuppressLint("ValidFragment")
    public FragmentEventi(List<Evento> fakeList, boolean visibilitaBottoniBassi) {
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
        View view = inflater.inflate(R.layout.fragment_sindaco_iscrizioni, container, false);
        initRecyler(view);
        return view;
    }

    private void initRecyler(View view){
        //TODO: per ora semplice con il caso banale, è poi da rendere più complesso
        TextView messaggio = view.findViewById(R.id.messaggio);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayout bottomLayout = view.findViewById(R.id.bottom_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Evento> eventi = fakeList;
        if(visibilitaBottoniBassi){
            bottomLayout.setVisibility(View.VISIBLE);
        }else{
            bottomLayout.setVisibility(View.GONE);
        }

        if(eventi.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            messaggio.setVisibility(View.GONE);
            FragmentEventiRecyclerAdapter adapter = new FragmentEventiRecyclerAdapter(eventi);
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            messaggio.setVisibility(View.VISIBLE);
        }
    }

}
