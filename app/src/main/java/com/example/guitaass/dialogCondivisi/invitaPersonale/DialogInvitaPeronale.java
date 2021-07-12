package com.example.guitaass.dialogCondivisi.invitaPersonale;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;
import com.example.guitaass.retrofit.utente.RetrofitClient;
import com.example.guitaass.sindaco.fragmentMioComune.fragmentElencoPersone.FragmentElencoPersone;
import com.example.guitaass.sindaco.fragmentMioComune.fragmentElencoPersone.PersoneRecyclerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogInvitaPeronale extends Dialog {

    private String TAG = "DialogInvitaPeronale";


    private Context context;
    private SharedPreferences shpr;
    private View view;


    public DialogInvitaPeronale(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //view = onCreatePanelView(1);
        setContentView(R.layout.dialog_invita_personale);
        //LayoutInflater inflater = getLayoutInflater();
        //view = inflater.inflate(R.layout.dialog_invita_personale, null);

        Log.d(TAG, "DialogInvitaPeronale onCreate view = : " + view);

        shpr = PreferenceManager.getDefaultSharedPreferences(context);

        view =getWindow().getDecorView();

        caricaUtenti();
    }

    private void caricaUtenti(){
        //se questo valore è a true vuol dire che devo caricare tutti gli utenti che lavorano già per il comune
        Call<List<Utente>> callUtente = RetrofitClient
                .getInstance(context)
                .getMyApi()
                .caricaNonDipendenti("Bearer " + shpr.getString("token", ""));

        callUtente.enqueue(new Callback<List<Utente>>() {
            @Override
            public void onResponse(Call<List<Utente>> call, Response<List<Utente>> response) {
                List<Utente> utenti = response.body();
                if(utenti != null){
                    Toast.makeText(context, "trovati " + utenti.size() + " non dipendenti", Toast.LENGTH_LONG).show();
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
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_2);
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
