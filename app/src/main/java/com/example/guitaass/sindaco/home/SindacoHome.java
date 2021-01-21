package com.example.guitaass.sindaco.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.R;
import com.example.guitaass.condivisi.fragmentEventi.FragmentEventi;
import com.example.guitaass.sindaco.fragmentComuniSeguiti.SindacoComuniSeguiti;
import com.example.guitaass.sindaco.fragmentEventi.SindacoEventi;
import com.example.guitaass.sindaco.fragmentMappa.SindacoMappa;
import com.example.guitaass.sindaco.fragmentMioComune.SindacoMioComune;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SindacoHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sindaco_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HOME");
        actionBar.setSubtitle(getIntent().getStringExtra("Username"));

        TabLayout tabLayout = findViewById(R.id.top_tab_menu);


        final Context context = this;
        View view = this.getCurrentFocus();
        //Toast.makeText(context, "tab = " + tabLayout, Toast.LENGTH_SHORT).show();

        //Imposto il tab che si vede di default
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentEventi fragment = new FragmentEventi(fakeRecyclerFill());
        fragmentTransaction.replace(R.id.fragment, fragment, "Iscrizioni").addToBackStack(null).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("onTabSelected", "****>>>elemento selezionato: " + tab.getPosition());
                int selezionato = tab.getPosition();
                //Log.d("onTabSelected", "elemento selezionato: " + selezionato);
                switch (selezionato){
                    case 0:{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        //cleanFragmentManager(fragmentManager);
                        FragmentEventi fragment = new FragmentEventi(fakeRecyclerFill());
                        fragmentTransaction.replace(R.id.fragment, fragment, "Iscrizioni").addToBackStack(null).commit();
                        //Toast.makeText(context, "iscrizioni", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case 1:{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        //cleanFragmentManager(fragmentManager);
                        SindacoMioComune fragment = new SindacoMioComune();
                        fragmentTransaction.replace(R.id.fragment, fragment, "MioComune").addToBackStack(null).commit();
                        //Toast.makeText(context, "mio comune", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case 2:{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        SindacoComuniSeguiti fragment = new SindacoComuniSeguiti();
                        fragmentTransaction.replace(R.id.fragment, fragment, "ComuniSeguiti").addToBackStack(null).commit();
                    }

                    case 3:{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        SindacoEventi fragment = new SindacoEventi();
                        fragmentTransaction.replace(R.id.fragment, fragment, "Eventi").addToBackStack(null).commit();
                    }

                    case 4:{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        SindacoMappa fragment = new SindacoMappa();
                        fragmentTransaction.replace(R.id.fragment, fragment, "Mappa").addToBackStack(null).commit();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sindaco_home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.aiuto:{
                Intent intent = new Intent(getBaseContext(), SindacoAiuto.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

        private List<Evento> fakeRecyclerFill(){
        List<Evento> list = new ArrayList<>();
        list.add(new Evento((long)1, "prova", 10, 2,
                true, "evento di prova fake per verificare il corretto funzionamento dell'app",
                "occhio a u coviddi", null, null, 1, 1));
        list.add(new Evento((long)2, "festa delle ciule piene", 100, 2,
                false, "evento tipico di Milanere, sono svariate le edizioni di questo evento che ricorre da più di 50 anni dove i protagonisti sono sempre stati: produttori locali, bande e scuole. Punto di forza? Le ciule ripiene e le frittelle di mele!!!!",
                "non adatto a chi non gradisce i prodotti piemontesi, neh?!", null, null, 2, 2));
        return list;
    }
}