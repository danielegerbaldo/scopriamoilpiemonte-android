package com.example.guitaass.registrazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guitaass.DOM.Role;
import com.example.guitaass.DOM.TipoEvento;
import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;
import com.example.guitaass.retrofit.municipalityClient.Comune;
import com.example.guitaass.retrofit.municipalityClient.RetrofitMunicipality;
import com.example.guitaass.retrofit.tipoEventoServer.RetrofitTipoEventoClient;
import com.example.guitaass.retrofit.utente.LoginResponse;
import com.example.guitaass.retrofit.utente.RetrofitClient;
import com.example.guitaass.retrofit.utente.SignUpRequest;
import com.example.guitaass.sindaco.home.SindacoHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registrazione extends AppCompatActivity {

    private static final String TAG = "Registrazione";
    private SharedPreferences shpr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        shpr = PreferenceManager.getDefaultSharedPreferences(this);

        EditText emailE = findViewById(R.id.email);
        EditText nomeE = findViewById(R.id.nome);
        EditText cognomeE = findViewById(R.id.cognome);
        EditText passwordE = findViewById(R.id.password);
        EditText confermaPWE = findViewById(R.id.conferma_password);
        Button registrati = findViewById(R.id.registrati);

        Context context = this;

        Spinner comuniSpinner = findViewById(R.id.comuni_list);

        Call<List<Comune>> call = RetrofitMunicipality
                .getInstance(this)
                .getMyApi()
                .getListComuni();

        Log.d(TAG, "Call.lista comuni: " + call.request());

        /*
        SpinnerAdapterComuni spinnerAdapterComuni = new SpinnerAdapterComuni(new ArrayList<>(), context );
        comuniSpinner.setAdapter(spinnerAdapterComuni);
        */
        call.enqueue(new Callback<List<Comune>>() {
            @Override
            public void onResponse(Call<List<Comune>> call, Response<List<Comune>> response) {
                if(response.body() != null){
                    Toast.makeText(context, "lista comuni size: " + response.body().size(), Toast.LENGTH_SHORT).show();
                    SpinnerAdapterComuni spinnerAdapterComuni = new SpinnerAdapterComuni(response.body(), context );
                    comuniSpinner.setAdapter(spinnerAdapterComuni);
                }else{
                    Toast.makeText(context, "Elenco Comuni: Errore dal server", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Comune>> call, Throwable t) {
                Toast.makeText(context, "Elenco Comuni: Errore dal server", Toast.LENGTH_SHORT).show();
            }
        });

        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordE.getText().toString();
                String confermaPW = confermaPWE.getText().toString();
                if(!password.equals(confermaPW)){
                    Toast.makeText(getApplicationContext(), "LE PASSWORD NON CORRISPONDONO!!", Toast.LENGTH_LONG).show();
                }else{
                    String email = emailE.getText().toString();
                    String nome = nomeE.getText().toString();
                    String cognome = cognomeE.getText().toString();
                    List<Role> ruoli = new ArrayList<>();
                    ruoli.add(Role.ROLE_CLIENT);
                    SignUpRequest signUpRequest = new SignUpRequest();
                    signUpRequest.setCf("");
                    signUpRequest.setCognome(cognome);
                    signUpRequest.setComuneResidenza(((Comune) comuniSpinner.getSelectedItem()).getIstat());
                    signUpRequest.setDipendenteDiComune((long) -1);
                    signUpRequest.setEmail(email);
                    signUpRequest.setNome(nome);
                    signUpRequest.setPassword(password);
                    signUpRequest.setRoles(ruoli);
                    signUpRequest.setTelefono("");
                    iscrivi(signUpRequest);
                }
            }
        });
    }

    private void iscrivi(SignUpRequest signUpRequest) {

        //richiesta al server
        Call<LoginResponse> call = RetrofitClient.getInstance(getApplicationContext()).getMyApi().signUp(signUpRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body() != null){
                    Toast.makeText(getApplicationContext(), "registrato con id = " + response.body().getId(), Toast.LENGTH_LONG).show();
                    ingresso(response.body().getId(), response.body().getEmail(), "");
                }else{
                    Toast.makeText(getApplicationContext(), "richiesta inviata ma errore", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "errore invio richiesta", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void ingresso(long utenteID, String email, String password){
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
                Utente utente = response.body();
                if(utente != null){
                    Toast.makeText(getApplicationContext(), "utente trovato: " + response.body().getNome() + " " + response.body().getCognome(), Toast.LENGTH_LONG).show();
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


}