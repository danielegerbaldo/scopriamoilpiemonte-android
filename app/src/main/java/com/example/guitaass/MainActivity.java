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
import com.example.guitaass.classiComode.RichiestaLogin;
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
                Call<Map<String, String>> call = RetrofitClient.getInstance(getApplicationContext()).getMyApi().login(mapdata);
                Log.d(TAG, "call: b: " + call.request().body().toString());
                Log.d(TAG, "call: " + call.request().toString());

                //TODO: AGGIUNGI IL DIALOG PER DARE FEEDBACK
                //mando la richiesta
                call.enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        //superListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, oneHeroes));
                        //Toast.makeText(getApplicationContext(), stringResponse, Toast.LENGTH_LONG).show();
                        //ingresso(response, emailEdit.getText().toString());
                        Log.d(TAG, "onResponse: r: " + response.toString());
                        if(response.body() != null){
                            //se il body è nullo, vuol dire che il server non ha mandato un HTTP.OK, quindi lo troviamo in bodyError
                            Log.d(TAG, "onResponse: body: " + response.body().toString());
                            Log.d(TAG, "onResponse: body: " + response.body().get("ruolo"));
                            ingresso(response.body(), emailEdit.getText().toString());

                        }else {
                            //il server ha restituito un qualche errore
                            Log.d(TAG, "onResponse: bad body: " + response.errorBody().toString());
                            Log.d(TAG, "onResponse: error message: " + response.message());
                            try {
                                //se riesco ad ottenere l'errorbody lo mostro
                                String t = response.errorBody().string();
                                Map<String, String> temp = new Gson().fromJson(t, Map.class);
                                //mostro l'errore resituito dal server
                                Toast.makeText(getApplicationContext(), temp.get("errore"), Toast.LENGTH_LONG).show();
                                Log.d(TAG, "onResponse: bad body: error: " + temp.get("errore"));
                            } catch (IOException e) {
                                //altrimenti comunico un errore generico
                                Toast.makeText(getApplicationContext(), "non è stato possibile ottenere l'errore dal server", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
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

    private void ingresso(Map<String, String> valori, String email){
        Intent intent = new Intent();
        intent.putExtra("Nome", valori.get("nome"));
        intent.putExtra("Cognome", valori.get("cognome"));
        intent.putExtra("Email", email);

        //mi salvo nelle shared preference il ruolo e se è un pubblicatore mi salvo anche il comune
        shpr.edit().putLong("utente_id", Long.parseLong(valori.get("utente_id"))).commit();

        if(valori.get("ruolo").equals("sindaco")){
            intent.setClass(getApplicationContext(), SindacoHome.class);
            Toast.makeText(getApplicationContext(), "login come sindaco di: " + Long.parseLong(valori.get("comune_id")), Toast.LENGTH_LONG).show();
            shpr.edit().putLong("comune_id", Long.parseLong(valori.get("comune_id"))).commit();
            Log.d(TAG, "> login: utente_id = " + Long.parseLong(valori.get("utente_id")) + "; comune_id = " + Long.parseLong(valori.get("comune_id")));
            //startActivity(intent);
        }else{
            intent.setClass(getApplicationContext(), UtenteHome.class);
        }
        startActivity(intent);
    }

    /*private void ingresso(Response<String> response, String email) {
        Map<String, String> valoriRisposta;
        //se si ha ottenuto un errore il body non sarà nel body ma in errorbody, quindi bisogna distinguere i due casi
        if(response.code() == HttpsURLConnection.HTTP_OK){
            Log.d(TAG, "ingresso: body = " + response.body());
            valoriRisposta = new Gson().fromJson(response.body(), Map.class);
        }else{
            try {
                valoriRisposta = new Gson().fromJson(response.errorBody().string(), Map.class);
            } catch (IOException e) {
                valoriRisposta = null;
            }
        }
        if(valoriRisposta != null){
            if(response.code() == HttpsURLConnection.HTTP_OK){
                Intent intent = new Intent();
                intent.putExtra("Nome", valoriRisposta.get("nome"));
                intent.putExtra("Cognome", valoriRisposta.get("cognome"));
                intent.putExtra("Email", email);

                if(valoriRisposta.get("ruolo").equals("sindaco")){
                    intent.setClass(getApplicationContext(), SindacoHome.class);
                    intent.putExtra("Comune", 1);
                    //startActivity(intent);
                }else{
                    intent.setClass(getApplicationContext(), UtenteHome.class);
                }
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),valoriRisposta.get("errore"),Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Problema con la MAP",Toast.LENGTH_SHORT).show();
        }
    }*/

    private void preferenzeCondivise() {
        if (shpr.getString("IP", "NOTFOUND").equals("NOTFOUND")){
            shpr.edit().putString("IP", "localhost").commit();
        }
    }

    public String generaBody(String email, String password){
        Gson gson = new Gson();
        String json = gson.toJson(new RichiestaLogin(password, email));
        Log.d("JSON", "¡¡¡¡¡¡¡json: " + json);
        return json;
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