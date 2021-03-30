package com.example.guitaass.fragmentCondivisi.fragmentEventi;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.example.guitaass.retrofit.eventServer.RetrofitEventClient;
import com.example.guitaass.sindaco.fragmentMioComune.SindacoMioComune;

//import com.example.guitaass.fragmentCondivisi.FragmentCreaEvento.FragmentCreaEvento;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEventi extends android.app.Fragment {

    private String TAG = "FragmentEventi";

    private boolean visibilitaBottoniBassi;

    private FragmentEventiRecyclerAdapter adapter;

    private View view;

    private List<Evento> listaEventi;

    private int visualizzazione;
    private long utenteID;
    private long comuneID;

    private Context context;

    private int disposizioneBottoni;

    /*public FragmentEventi() {
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
    }*/

    public FragmentEventi(){}

    @SuppressLint("ValidFragment")
    public FragmentEventi(int visualizzazione, long utenteID, long comuneID, int disposizioneBottoni,boolean visibilitaBottoniBassi){
        this.visualizzazione = visualizzazione;
        this.utenteID = utenteID;
        this.comuneID = comuneID;
        this.visibilitaBottoniBassi = visibilitaBottoniBassi;
        this.disposizioneBottoni = disposizioneBottoni;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sindaco_iscrizioni, container, false);
        //initRecyler(view);
        context = view.getContext();
        this.view = view;
        dispatcher();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void dispatcher(){
        switch (visualizzazione){
            case 1:{    //visualizzo tutti gli eventi del comune indicato
                tuttiEventiComune();
                break;
            }
            case 2:{    //visualizzo tutti gli eventi a cui non sono iscritto
                tuttiEventiNonIscritto();
            }
            case 3:{    //visualizzo tutti i miei eventi del comune
                iscrizioniUtente();
                break;
            }
            case 4:{    //visualizzo gli eventi a cui sono iscritto
                initRecyler(new ArrayList<>());
                break;
            }
        }
    }

    private void tuttiEventiComune(){
        Call<List<Evento>> call = RetrofitEventClient.getInstance(view.getContext()).getMyAPI().ottieniEventiDiComune(comuneID);
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.body() != null){
                    initRecyler(response.body());
                }else{
                    Toast.makeText(context, "errore dal server ", Toast.LENGTH_SHORT).show();
                    initRecyler(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(context, "impossibile comunicare col server", Toast.LENGTH_SHORT).show();
                initRecyler(new ArrayList<>());
            }
        });
    }

    private void tuttiEventiNonIscritto(){
        Call<List<Evento>> call = RetrofitEventClient.getInstance(view.getContext()).getMyAPI().ottieniEventiUtenteNonIscritto(utenteID);
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.body() != null){
                    initRecyler(response.body());
                }else{
                    Toast.makeText(context, "errore dal server ", Toast.LENGTH_SHORT).show();
                    initRecyler(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(context, "impossibile comunicare col server", Toast.LENGTH_SHORT).show();
                initRecyler(new ArrayList<>());
            }
        });
    }

    private  void iscrizioniUtente(){
        Call<List<Evento>> call = RetrofitEventClient.getInstance(view.getContext()).getMyAPI().prenotazioniUtente(utenteID);
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.body() != null){
                    initRecyler(response.body());
                }else{
                    Toast.makeText(context, "errore dal server ", Toast.LENGTH_SHORT).show();
                    initRecyler(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(context, "impossibile comunicare col server", Toast.LENGTH_SHORT).show();
                initRecyler(new ArrayList<>());
            }
        });
    }

    private void initRecyler(List<Evento> eventiIn){

        //elementi
        TextView messaggio = view.findViewById(R.id.messaggio);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayout bottomLayout = view.findViewById(R.id.bottom_layout);



        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Evento> eventi = eventiIn;

        Log.d(TAG, "initRecyler: visibilità bottoni = " + visibilitaBottoniBassi);

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

            adapter = new FragmentEventiRecyclerAdapter(eventi, disposizioneBottoni);

            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            messaggio.setVisibility(View.VISIBLE);
        }
    }

    public FragmentEventiRecyclerAdapter getRecyclerAdapter () {return  adapter;}


}
