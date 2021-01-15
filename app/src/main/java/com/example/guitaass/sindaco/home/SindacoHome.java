package com.example.guitaass.sindaco.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guitaass.R;
import com.example.guitaass.sindaco.fragmentIscrizioni.SindacoIscrizioni;
import com.example.guitaass.sindaco.fragmentMioComune.SindacoMioComune;

public class SindacoHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sindaco_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HOME");
        actionBar.setSubtitle(getIntent().getStringExtra("Username"));
        //View fragment = findViewById(R.id.fragment);
        //View fragment = findViewById(R.id.fragment_layout);
        //SindacoMioComune mioComune = new SindacoMioComune();
        Spinner spinner = findViewById(R.id.spinner);

        /*String elementoSelezionato = spinner.getSelectedItem().toString();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();*/

        //*cleanFragmentManager(fragmentManager);
        //SindacoIscrizioni fragment = new SindacoIscrizioni();
        //getFragmentManager().beginTransaction().add(R.id.fragment, fragment, "Iscrizioni").addToBackStack(null).commit();
        //FragmentManager fragmentManager = getFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });

        /*switch (elementoSelezionato){
            case "Iscrizioni":{
                cleanFragmentManager(fragmentManager);
                SindacoMioComune fragment = new SindacoMioComune();
                fragmentTransaction.replace(R.id.fragment, fragment, "Iscrizioni").addToBackStack(null).commit();
                break;
            }

            case "Mio Comune":{
                cleanFragmentManager(fragmentManager);
                SindacoMioComune fragment = new SindacoMioComune();
                fragmentTransaction.replace(R.id.fragment, fragment, "MioComune").addToBackStack(null).commit();
            }
        }*/

        /*String elementoSelezionato = spinner.getSelectedItem().toString();
                switch (elementoSelezionato){
                    case "Iscrizioni":{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        cleanFragmentManager(fragmentManager);
                        SindacoMioComune fragment = new SindacoMioComune();
                        fragmentTransaction.replace(R.id.fragment, fragment, "Iscrizioni").addToBackStack(null).commit();
                        break;
                    }

                    case "Mio Comune":{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        cleanFragmentManager(fragmentManager);
                        SindacoIscrizioni fragment = new SindacoIscrizioni();
                        fragmentTransaction.replace(R.id.fragment, fragment, "MioComune").addToBackStack(null).commit();
                        break;
                    }
                }*/
    }

    protected void cleanFragmentManager(FragmentManager fragmentManager){
        while(fragmentManager.getBackStackEntryCount() != 0){
            fragmentManager.popBackStack();
        }
    }
}