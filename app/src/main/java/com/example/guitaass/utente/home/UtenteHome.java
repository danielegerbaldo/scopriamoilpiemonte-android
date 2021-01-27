package com.example.guitaass.utente.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guitaass.R;

public class UtenteHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HOME");
        actionBar.setSubtitle(getIntent().getStringExtra("Username"));
    }
}
