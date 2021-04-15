package com.example.guitaass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guitaass.AsynkTask.RequestNetAsync;
import com.example.guitaass.DOM.Utente;
import com.example.guitaass.retrofit.API;
import com.example.guitaass.retrofit.RetrofitClient;
import com.example.guitaass.sindaco.home.SindacoAiuto;
import com.example.guitaass.sindaco.home.SindacoHome;
import com.example.guitaass.utente.home.UtenteHome;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SharedPreferences shpr;

    //message handler della risposta
    @SuppressLint("HandlerLeak")
    private final Handler messageHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
        resultReady(msg.getData().getString("RESULT"));
        }
    };

    private void resultReady(String result) {
        Toast.makeText(getApplicationContext(), "HTTP OK", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shpr = PreferenceManager.getDefaultSharedPreferences(this);

        preferenzeCondivise();

        Button registrati = findViewById(R.id.registrati);
        registrati.setOnClickListener(new View.OnClickListener() {
            //visualizza la activity per la registrazione
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Registrazione.class);
                startActivity(intent);
            }
        });

        Button accedi = findViewById(R.id.accedi);

        //TODO: aggiungere progress dialog

        //gestione del tasto "accedi"
        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEdit = findViewById(R.id.name);
                EditText passwordEdit = findViewById(R.id.password);

                //creo una Map in cui inserirò i dati della richiesta
                Map<String, String> mapdata = new HashMap<>();
                mapdata.put("email", emailEdit.getText().toString());
                mapdata.put("password", passwordEdit.getText().toString());

                //Creo una call per la richiesta tramite retrofit2
                Call<Utente> call = RetrofitClient.getInstance(getApplicationContext()).getMyApi().login(mapdata);
                Log.d(TAG, "call: b: " + call.request().body().toString());
                Log.d(TAG, "call: " + call.request().toString());

                //TODO: AGGIUNGI IL DIALOG PER DARE FEEDBACK
                //mando la richiesta
                call.enqueue(new Callback<Utente>() {
                    @Override
                    public void onResponse(Call<Utente> call, Response<Utente> response) {
                        Log.d(TAG, "onResponse: r: " + response.toString());
                        if(response.body() != null){
                            //se il body è nullo, vuol dire che il server non ha mandato un HTTP.OK, quindi lo troviamo in bodyError
                            Log.d(TAG, "onResponse: body: " + response.body().toString());
                            Log.d(TAG, "onResponse: body: " + response.body().getRuolo());
                            ingresso(response.body(), emailEdit, passwordEdit);
                        }else {
                            Toast.makeText(getApplicationContext(), "email o password errati", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Utente> call, Throwable t) {
                        //c'è stato qualche errore durante la comunicazione
                        Log.d(TAG, "onFailure: call  = " +  call.request().toString());
                        Log.d(TAG, "onFailure: throwable = " + t.getMessage() + "; " + t.getCause());
                        Log.d(TAG, "onFailure: throwable = " + t.toString());
                        Toast.makeText(getApplicationContext(), "errore durante la comunicazione", Toast.LENGTH_LONG).show();
                    }

                });

            }
        });
    }

    private void ingresso(Utente utente, EditText email, EditText password){
        if(utente != null){
            //Toast.makeText(getApplicationContext(), "login come " + utente.getEmail(), Toast.LENGTH_LONG).show();
            //creo un intento e gli aggiungo i dati che servono solo alla home dell'utente
            Intent intent = new Intent();
            intent.putExtra("Nome", utente.getNome());
            intent.putExtra("Cognome", utente.getCognome());
            intent.putExtra("Email", utente.getEmail());

            //aggiungo nelle shared preference i dati che serviranno per tutta l'applicazione
            shpr.edit().putLong("utente_id", utente.getId()).apply();
            intent.setClass(getApplicationContext(), SindacoHome.class);
            if(utente.getRuolo().equals("sindaco")){
                Toast.makeText(getApplicationContext(), "login come sindaco di: " + utente.getComune(), Toast.LENGTH_LONG).show();
                shpr.edit().putLong("comune_id", utente.getComune()).apply();
                shpr.edit().putString("ruolo", "sindaco").apply();
                //shpr.edit().putString("email", utente.getEmail());
            }else{
                Toast.makeText(getApplicationContext(), "login come utente ", Toast.LENGTH_LONG).show();
                shpr.edit().putString("ruolo", "utente").apply();
                //intent.setClass(getApplicationContext(), UtenteHome.class);
            }
            email.setText("");
            password.setText("");
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "c'è stato un errore interno", Toast.LENGTH_LONG).show();
        }
    }

    private void preferenzeCondivise() {
        if (shpr.getString("IP", "NOTFOUND").equals("NOTFOUND")){
            shpr.edit().putString("IP", "localhost").commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.impostazioni_avanzate:{
                Intent intent = new Intent(getBaseContext(), ImpostazioniAvanzate.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }
}