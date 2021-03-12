package com.example.guitaass.fragmentCondivisi.fragmentEventi;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.R;
import com.example.guitaass.dialogCondivisi.creaEvento.DialogCreaEvento;
import com.example.guitaass.dialogCondivisi.creaSondaggio.DialogCreaSondaggio;

//import com.example.guitaass.fragmentCondivisi.FragmentCreaEvento.FragmentCreaEvento;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initRecyler(View view){
        //TODO: per ora semplice con il caso banale, è poi da rendere più complesso

        //elementi
        TextView messaggio = view.findViewById(R.id.messaggio);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayout bottomLayout = view.findViewById(R.id.bottom_layout);



        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Evento> eventi = fakeList;

        /*FrameLayout fragmentContainerView = view.findViewById(R.id.fragment_crea_nuovo_evento_sondaggio);

        FrameLayout miaView = view.findViewById(R.id.fragment_sindaco_iscrizioni);

        RelativeLayout relativeLayout = view.findViewById(R.id.relative_layout);*/
        if(visibilitaBottoniBassi){
            bottomLayout.setVisibility(View.VISIBLE);
            //gestisco i bottoni
            Button creaEvento = view.findViewById(R.id.crea_evento);
            creaEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogCreaEvento dialogEvento = new DialogCreaEvento(v.getContext());
                    //dialogEvento.setCancelable(true);
                    dialogEvento.setCanceledOnTouchOutside(false);
                    dialogEvento.show();

                }
            });

            Button creaSondaggio = view.findViewById(R.id.crea_sondaggio);
            creaSondaggio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogCreaSondaggio dialogSondaggio = new DialogCreaSondaggio(v.getContext());
                    //dialogSondaggio.setCancelable(false);
                    dialogSondaggio.setCanceledOnTouchOutside(false);
                    dialogSondaggio.show();
                }
            });

        }else{
            bottomLayout.setVisibility(View.GONE);

        }
        //bottomLayout.setVisibility(View.GONE);


        if(eventi.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            messaggio.setVisibility(View.GONE);
            FragmentEventiRecyclerAdapter adapter;
            if(visibilitaBottoniBassi){
                //visualizza modifica e elimina
                adapter = new FragmentEventiRecyclerAdapter(eventi, 1);
            }else{
                adapter = new FragmentEventiRecyclerAdapter(eventi, 2);
            }

            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            messaggio.setVisibility(View.VISIBLE);
        }
    }

}
