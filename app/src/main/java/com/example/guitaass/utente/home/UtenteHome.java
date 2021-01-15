package com.example.guitaass.utente.home;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guitaass.R;
import com.example.guitaass.utente.UtenteEvents;

public class UtenteHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_home);
        Button visualizzaEventi = findViewById(R.id.visualizzaEventi);
        visualizzaEventi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UtenteEvents.class);
                intent.putExtra("Username", "utente");
                intent.putExtra("Email", "utente@gmail.com");
                startActivity(intent);
            }
        });

    }
}
