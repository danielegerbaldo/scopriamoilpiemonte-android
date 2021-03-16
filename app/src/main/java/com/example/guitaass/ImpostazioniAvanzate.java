package com.example.guitaass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ImpostazioniAvanzate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni_avanzate);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String ip = prefs.getString("IP", "NOTFOUND");
        EditText editIP = findViewById(R.id.edit_ip);
        editIP.setText(ip);

        Button conferma = findViewById(R.id.conferma);
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putString("IP", editIP.getText().toString()).commit();
                finish();
            }
        });

        Button annulla = findViewById(R.id.annulla);
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}