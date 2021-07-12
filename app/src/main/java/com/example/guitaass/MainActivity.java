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

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.registrazione.Registrazione;
import com.example.guitaass.retrofit.utente.LoginRequest;
import com.example.guitaass.retrofit.utente.LoginResponse;
import com.example.guitaass.retrofit.utente.RetrofitClient;
import com.example.guitaass.sindaco.home.SindacoHome;
import com.example.guitaass.webGoogleLogin.GoogleLogin;
import com.example.guitaass.webGoogleLogin.GoogleLogin2;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

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
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(emailEdit.getText().toString());
                loginRequest.setPassword(passwordEdit.getText().toString());

                //Creo una call per la richiesta tramite retrofit2
                Call<LoginResponse> call = RetrofitClient.getInstance(
                        getApplicationContext())
                        .getMyApi()
                        .login(loginRequest);
                Log.d(TAG, "call: b: " + call.request());
                Log.d(TAG, "call: " + call.request().toString());

                //TODO: AGGIUNGI IL DIALOG PER DARE FEEDBACK
                //mando la richiesta
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Log.d(TAG, "onResponse: r: " + response.toString());
                        if(response.body() != null){
                            //se il body è nullo, vuol dire che il server non ha mandato un HTTP.OK, quindi lo troviamo in bodyError
                            Toast.makeText(getApplicationContext(), "login con successo", Toast.LENGTH_LONG).show();
                            shpr.edit().putString("token", response.body().getAccessToken()).apply();

                            long userID = response.body().getId();

                            ingresso(response.body().getId(), emailEdit, passwordEdit);
                        }else {
                            Toast.makeText(getApplicationContext(), "email o password errati", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        //c'è stato qualche errore durante la comunicazione
                        Log.d(TAG, "onFailure: call  = " +  call.request().toString());
                        Log.d(TAG, "onFailure: throwable = " + t.getMessage() + "; " + t.getCause());
                        Log.d(TAG, "onFailure: throwable = " + t.toString());
                        Toast.makeText(getApplicationContext(), "errore durante la comunicazione", Toast.LENGTH_LONG).show();

                        //Toast.makeText(getApplicationContext(), "errore durante la comunicazione", Toast.LENGTH_LONG).show();
                    }

                });

            }
        });

        Button loginGoogleButton = findViewById(R.id.google_login);
        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GoogleLogin2.class);
                startActivity(intent);
            }
        });

    }

    private void ingresso(long utenteID, EditText email, EditText password){
        Call<Utente> callUtente = RetrofitClient
                .getInstance(getApplicationContext())
                .getMyApi()
                .infoUtente(utenteID, "Bearer " + shpr.getString("token", "")/*, utenteID*/);
        //callUtente.request().headers().newBuilder().add("Bearer", shpr.getString("token", "")).build();
        //callUtente.request()
        Log.d(TAG, "ingresso: request = " + callUtente.request());
        Log.d(TAG, "ingresso: request header = " + callUtente.request().headers());
        callUtente.enqueue(new Callback<Utente>() {
            @Override
            public void onResponse(Call<Utente> call, Response<Utente> response) {
                Toast.makeText(getApplicationContext(), "utente trovato: " + response.body().getNome() + " " + response.body().getCognome(), Toast.LENGTH_LONG).show();
                Utente utente = response.body();
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
                    Log.d(TAG, "ingresso: " + utente.getRuoli().get(0).getAuthority());
                    if(utente.getRuoli().get(0).getAuthority().equals("ROLE_MAYOR")){
                        Log.d(TAG, "ingresso: login come sindaco");
                        Toast.makeText(getApplicationContext(), "login come sindaco di: " + utente.getDipendenteDiComune(), Toast.LENGTH_LONG).show();
                        shpr.edit().putLong("comune_id", utente.getDipendenteDiComune()).apply();
                        shpr.edit().putString("ruolo", utente.getRuoli().get(0).getAuthority()).apply();
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

            @Override
            public void onFailure(Call<Utente> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "utente non trovato", Toast.LENGTH_LONG).show();
            }
        });

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