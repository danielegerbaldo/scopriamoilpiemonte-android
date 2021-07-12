package com.example.guitaass.sindaco.fragmentMioComune.fragmentElencoPersone;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;
import com.example.guitaass.dialogCondivisi.creaEvento.DialogCreaEvento;
import com.example.guitaass.dialogCondivisi.invitaPersonale.DialogInvitaPeronale;
import com.example.guitaass.retrofit.utente.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentElencoPersone extends Fragment {
    private static final String TAG = "FragmentElencoPersone";


    //private List<Utente> utenti;
    private SharedPreferences shpr;
    private boolean caricaLavoratori = false;
    private Context context;
    private View view;

    /*
    @SuppressLint("ValidFragment")
    public FragmentElencoPersone(List<Utente> utenti) {
        this.utenti = utenti;
    }
    */

    @SuppressLint("ValidFragment")
    public FragmentElencoPersone(boolean caricaLavoratori) {
        this.caricaLavoratori = caricaLavoratori;
    }

    public FragmentElencoPersone() {
        //utenti = new ArrayList<>();
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
        context = view.getContext();
        this.view = view;
        shpr = PreferenceManager.getDefaultSharedPreferences(context);
        Toast.makeText(context, "TAB personale", Toast.LENGTH_LONG).show();
        caricaUtenti();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button aggiungiPersonale = view.findViewById(R.id.aggiungi_personale);
        //aggiungi personale farà comparire un dialog
        aggiungiPersonale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInvitaPeronale dialog = new DialogInvitaPeronale(context);
                //dialog.setCanceledOnTouchOutside(false);
                //dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


                /*
                Call<List<Utente>> nonDipendenti = RetrofitClient
                        .getInstance(context)
                        .getMyApi()
                        .caricaNonDipendenti("Bearer " + shpr.getString("token", ""));

                Log.d(TAG, "richiesta: " + nonDipendenti.request());

                nonDipendenti.enqueue(new Callback<List<Utente>>() {
                    @Override
                    public void onResponse(Call<List<Utente>> call, Response<List<Utente>> response) {
                        if(response.body() != null){
                            Toast.makeText(v.getContext(), "Non dipendenti: " + response.body().size(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(v.getContext(), "Errore", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Utente>> call, Throwable t) {
                        Toast.makeText(v.getContext(), "Errore comunicazione", Toast.LENGTH_SHORT).show();
                    }
                });
                */

                //Toast.makeText(v.getContext(), "Funzione non ancora sviluppata", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void caricaUtenti(){
        //se questo valore è a true vuol dire che devo caricare tutti gli utenti che lavorano già per il comune
        long comuneID = shpr.getLong ("comune_id", -1);
        Call<List<Utente>> callUtente = RetrofitClient
                .getInstance(context)
                .getMyApi()
                .caricaLavoratoriComune(comuneID, "Bearer " + shpr.getString("token", ""));

        callUtente.enqueue(new Callback<List<Utente>>() {
            @Override
            public void onResponse(Call<List<Utente>> call, Response<List<Utente>> response) {
                List<Utente> utenti = response.body();
                if(utenti != null){
                    Toast.makeText(context, "trovati " + utenti.size() + " dipendenti", Toast.LENGTH_LONG).show();
                    initRecyler(view, utenti);
                }else{
                    Toast.makeText(context, "Errore nella chiamata", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Utente>> call, Throwable t) {
                Toast.makeText(context, "Errore comunicazione col server", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initRecyler(View view, List<Utente> utenti){
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
