package com.example.guitaass.sindaco.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.R;
import com.example.guitaass.fragmentCondivisi.fragmentEventi.FragmentEventi;
import com.example.guitaass.sindaco.fragmentComuniSeguiti.SindacoComuniSeguiti;
import com.example.guitaass.sindaco.fragmentEventi.SindacoEventi;
import com.example.guitaass.sindaco.fragmentMappa.SindacoMappa;
import com.example.guitaass.sindaco.fragmentMioComune.SindacoMioComune;
import com.example.guitaass.utente.Impostazioni;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SindacoHome extends AppCompatActivity {

    private SharedPreferences shpr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sindaco_home);
        shpr = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Intent intent = getIntent();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setSubtitle(getIntent().getStringExtra("Email"));

        TabLayout tabLayout = findViewById(R.id.top_tab_menu);

        String ruolo = shpr.getString("ruolo", "utente");
        if(ruolo.equals("utente")){
            actionBar.setTitle("HOME Utente");
            tabLayout.removeTabAt(1);
        }else{
            actionBar.setTitle("HOME Sindaco");
        }


        final Context context = this;
        View view = this.getCurrentFocus();
        //Toast.makeText(context, "tab = " + tabLayout, Toast.LENGTH_SHORT).show();

        //Imposto il tab che si vede di default
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentEventi fragment = new FragmentEventi(3, shpr.getLong("utente_id", -1), shpr.getLong("comune_id", -1), 2,false);
        fragmentTransaction.replace(R.id.fragment, fragment, "Eventi").addToBackStack(null).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Log.d("onTabSelected", "****>>>elemento selezionato: " + tab.getPosition());
                int selezionato = tab.getPosition();

                //per rendere l'app una applicazione singola senza distinzione tra utente e sindaco
                if(ruolo.equals("utente")){
                    //sono un utente normale
                    switch (selezionato){
                        case 0:{    //iscrizioni del sindaco
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            FragmentEventi fragment = new FragmentEventi(3, shpr.getLong("utente_id", -1), shpr.getLong("comune_id", -1), 2,false);
                            fragmentTransaction.replace(R.id.fragment, fragment, "Eventi").addToBackStack(null).commit();
                            break;
                        }
                        case 1:{    //eventi
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            FragmentEventi fragment = new FragmentEventi(2, shpr.getLong("utente_id", -1), shpr.getLong("comune_id", -1),4 ,false);
                            fragmentTransaction.replace(R.id.fragment, fragment, "Eventi").addToBackStack(null).commit();
                            break;
                        }

                        case 2:{    //comuni seguiti
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            SindacoComuniSeguiti fragment = new SindacoComuniSeguiti();
                            fragmentTransaction.replace(R.id.fragment, fragment, "ComuniSeguiti").addToBackStack(null).commit();
                            break;
                        }

                        case 3:{    //mappa
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            SindacoMappa fragment = new SindacoMappa();
                            fragmentTransaction.replace(R.id.fragment, fragment, "Mappa").addToBackStack(null).commit();
                            break;
                        }
                    }
                }else{
                    //sono un utente che può pubblicare
                    switch (selezionato){
                        case 0:{    //iscrizioni del sindaco
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            FragmentEventi fragment = new FragmentEventi(3, shpr.getLong("utente_id", -1), shpr.getLong("comune_id", -1), 2,false);
                            fragmentTransaction.replace(R.id.fragment, fragment, "Eventi").addToBackStack(null).commit();
                            break;
                        }

                        case 1:{    //mio comune
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            SindacoMioComune fragment = new SindacoMioComune();
                            fragmentTransaction.replace(R.id.fragment, fragment, "MioComune").addToBackStack(null).commit();
                            //Toast.makeText(context, "mio comune", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case 2:{    //eventi
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            FragmentEventi fragment = new FragmentEventi(2, shpr.getLong("utente_id", -1), shpr.getLong("comune_id", -1),4 ,false);
                            fragmentTransaction.replace(R.id.fragment, fragment, "Eventi").addToBackStack(null).commit();
                            break;
                        }

                        case 3:{    //comuni seguiti
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            SindacoComuniSeguiti fragment = new SindacoComuniSeguiti();
                            fragmentTransaction.replace(R.id.fragment, fragment, "ComuniSeguiti").addToBackStack(null).commit();
                            break;
                        }

                        case 4:{    //mappa
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            SindacoMappa fragment = new SindacoMappa();
                            fragmentTransaction.replace(R.id.fragment, fragment, "Mappa").addToBackStack(null).commit();
                            break;
                        }
                    }

                }
                //Log.d("onTabSelected", "elemento selezionato: " + selezionato);
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

            case R.id.esci:{
                shpr.edit().remove("utente_id").apply();
                shpr.edit().remove("comune_id").apply();
                finish();
                break;
            }

            case R.id.impostazioni:{
                Intent intent = new Intent(getBaseContext(), Impostazioni.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }



    @Override
    public void onBackPressed() {
        Toast.makeText(this, "funzione disabilitata", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(this, "funzione disabilitata", Toast.LENGTH_SHORT).show();
        return true;
    }
}