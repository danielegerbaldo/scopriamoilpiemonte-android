package com.example.guitaass.utente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guitaass.R;

public class Impostazioni extends AppCompatActivity {

    private SharedPreferences shpr;
    private boolean visibilitaModificaPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        shpr = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        getSupportActionBar().setTitle("Impostazioni");
        getSupportActionBar().setSubtitle(shpr.getString("email", ""));

        nuovaPassword();
    }

    private void nuovaPassword(){
        TextView titoloModificaPasswod = findViewById(R.id.titolo_modifica_password);

        EditText vecchiaPasswordEdit = findViewById(R.id.password_vecchia);
        EditText nuovaPasswordEdit = findViewById(R.id.nuova_password);
        EditText confermaPasswordEdit = findViewById(R.id.conferma_password);

        Button conferma = findViewById(R.id.conferma);
        Button annulla = findViewById(R.id.annulla);

        titoloModificaPasswod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiaVisibilitaModificaPassword();
            }
        });

        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vecchiaPasswordEdit.setText("");
                nuovaPasswordEdit.setText("");
                confermaPasswordEdit.setText("");
                cambiaVisibilitaModificaPassword();
            }
        });

    }

    private void cambiaVisibilitaModificaPassword(){
        visibilitaModificaPassword = !visibilitaModificaPassword;
        if(visibilitaModificaPassword){
            findViewById(R.id.corpo_modifica_password).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.corpo_modifica_password).setVisibility(View.GONE);
        }
    }
}