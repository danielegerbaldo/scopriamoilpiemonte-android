package com.example.guitaass.utente.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;
import com.example.guitaass.utente.fragmentUtente.FragmentUtenti;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class UtenteHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HOME");
        actionBar.setSubtitle(getIntent().getStringExtra("Username"));

        TabLayout tabLayout = findViewById(R.id.top_tab_menu_utente);

        final Context context = this;
        View view = this.getCurrentFocus();
        //Toast.makeText(context, "tab = " + tabLayout, Toast.LENGTH_SHORT).show();

        //Imposto il tab che si vede di default
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentUtenti fragment = new FragmentUtenti(fakeRecyclerFill(), true);
        fragmentTransaction.replace(R.id.fragment_utente, fragment, "Iscrizioni").addToBackStack(null).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("onTabSelected", "****>>>elemento selezionato: " + tab.getPosition());
                int selezionato = tab.getPosition();
                switch (selezionato){
                    case 0: {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        //cleanFragmentManager(fragmentManager);
                        FragmentUtenti fragment = new FragmentUtenti(fakeRecyclerFill(), true);
                        fragmentTransaction.replace(R.id.fragment_utente, fragment, "Iscrizioni").addToBackStack(null).commit();
                        //Toast.makeText(context, "iscrizioni", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        break;
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


    private List<Utente> fakeRecyclerFill(){
        List<Utente> list = new ArrayList<>();
        list.add(new Utente(1,"Flavio", "Roman","status"));
        list.add(new Utente(2,"Mattia","Martello","status"));
        list.add(new Utente(3,"Daniele","Gerbaldo","status"));
        list.add(new Utente(4,"Francesco","Conforti","status"));
        return list;
    }

}
