package com.example.guitaass.sindaco.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.guitaass.R;
import com.example.guitaass.sindaco.fragmentIscrizioni.SindacoIscrizioni;
import com.example.guitaass.sindaco.fragmentMioComune.SindacoMioComune;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

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
        SindacoIscrizioni fragment = new SindacoIscrizioni();
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
                        SindacoIscrizioni fragment = new SindacoIscrizioni();
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
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String elementoSelezionato = spinner.getSelectedItem().toString();
                switch (elementoSelezionato){
                    case "Iscrizioni":{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        //cleanFragmentManager(fragmentManager);
                        SindacoIscrizioni fragment = new SindacoIscrizioni();
                        fragmentTransaction.replace(R.id.fragment, fragment, "Iscrizioni").addToBackStack(null).commit();
                        Toast.makeText(view.getContext(), "iscrizioni", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case "Mio Comune":{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        //cleanFragmentManager(fragmentManager);
                        SindacoMioComune fragment = new SindacoMioComune();
                        fragmentTransaction.replace(R.id.fragment, fragment, "MioComune").addToBackStack(null).commit();
                        Toast.makeText(view.getContext(), "mio comune", Toast.LENGTH_SHORT).show();
                        break;
                    }


                    default:{
                        Toast.makeText(view.getContext(), "errore/non ancora sviluppato", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sindaco_home_menu, menu);
        return true;
    }*/

    protected void cleanFragmentManager(FragmentManager fragmentManager){
        while(fragmentManager.getBackStackEntryCount() != 0){
            fragmentManager.popBackStack();
        }
    }
}