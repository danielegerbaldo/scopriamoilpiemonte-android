package com.example.guitaass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registrazione extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        EditText emailE = findViewById(R.id.email);
        EditText nomeE = findViewById(R.id.nome);
        EditText cognomeE = findViewById(R.id.cognome);
        EditText passwordE = findViewById(R.id.password);
        EditText confermaPWE = findViewById(R.id.conferma_password);
        Button registrati = findViewById(R.id.registrati);

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
                    iscrivi(email, nome, cognome, password);
                }
            }
        });
    }

    private void iscrivi(String email, String nome, String cognome, String password) {
        Map<String, String> bodyRichiesta = new HashMap<>();
        bodyRichiesta.put("email", email);
        bodyRichiesta.put("nome", nome);
        bodyRichiesta.put("cognome", cognome);
        bodyRichiesta.put("password", password);

        //richiesta al server
        Call<Map<String, String>> call = RetrofitClient.getInstance(getApplicationContext()).getMyApi().registrati(bodyRichiesta);

        call.enqueue(new Callback<Map<String, String>>() {

            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                String risposta = response.body().get("risposta");
                Toast.makeText(getApplicationContext(), risposta, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Comunicazione col server fallita", Toast.LENGTH_LONG).show();

            }
        });
    }

}