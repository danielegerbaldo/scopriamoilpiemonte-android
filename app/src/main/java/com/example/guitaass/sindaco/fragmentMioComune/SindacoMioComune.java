package com.example.guitaass.sindaco.fragmentMioComune;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;
import com.example.guitaass.condivisi.fragmentEventi.FragmentEventi;
import com.example.guitaass.sindaco.fragmentMioComune.fragmentElencoPersone.FragmentElencoPersone;
import com.example.guitaass.sindaco.fragmentMioComune.fragmentInfo.FragmentInfo;
import com.example.guitaass.sindaco.fragmentMioComune.fragmentSegnalazioni.FragmentSegnalazioni;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SindacoMioComune extends Fragment {

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View view = getView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sindaco_mio_comune, container, false);
        //Context context = view.getContext();
        //TabLayout tabLayout = view.findViewById(R.id.mio_comune_tab_layout);
        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentEventi fragment = new FragmentEventi(fakeRecyclerFill());
        fragmentTransaction.replace(R.id.fragment, fragment, "Iscrizioni").addToBackStack(null).commit();*/
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.mio_comune_tab_layout);
        Context context = view.getContext();
        //Log.d("FragmentMioComune", "***********context = " + context);

        //imposto il tab che si vede di default
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentEventi fragment = new FragmentEventi(fakeRecyclerFill(), true);
        fragmentTransaction.replace(R.id.fragment2, fragment, "EventiComune").addToBackStack(null).commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selezionato = tab.getPosition();
                //Toast.makeText(context, "tab " + selezionato, Toast.LENGTH_SHORT).show();
                switch (selezionato){
                    case 0:{    //eventi
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentEventi fragment = new FragmentEventi(fakeRecyclerFill(), true);
                        fragmentTransaction.replace(R.id.fragment2, fragment, "EventiComune").addToBackStack(null).commit();
                        break;
                    }

                    case 1:{    //personale
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentElencoPersone fragment = new FragmentElencoPersone(fakeUtenteRecyclerFill1());
                        fragmentTransaction.replace(R.id.fragment2, fragment, "Personale").addToBackStack(null).commit();
                        break;
                    }

                    case 2:{    //info
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentInfo fragment = new FragmentInfo();
                        fragmentTransaction.replace(R.id.fragment2, fragment, "Personale").addToBackStack(null).commit();
                        break;
                    }

                    case 3:{    //iscritti
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentElencoPersone fragment = new FragmentElencoPersone();
                        fragmentTransaction.replace(R.id.fragment2, fragment, "Iscritti").addToBackStack(null).commit();
                        break;
                    }

                    case 4:{    //segnalazioni
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentSegnalazioni fragment = new FragmentSegnalazioni();
                        fragmentTransaction.replace(R.id.fragment2, fragment, "Segnalazioni").addToBackStack(null).commit();
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
    }

    private List<Evento> fakeRecyclerFill(){
        List<Evento> list = new ArrayList<>();
        list.add(new Evento((long)1, "prova evento mio comune1", 10, 2,
                true, "evento di prova fake per verificare il corretto funzionamento dell'app",
                "occhio a u coviddi", null, null, 1, 1));
        list.add(new Evento((long)2, "prova evento mio comune2", 100, 2,
                false, "evento tipico di Milanere, sono svariate le edizioni di questo evento che ricorre da più di 50 anni dove i protagonisti sono sempre stati: produttori locali, bande e scuole. Punto di forza? Le ciule ripiene e le frittelle di mele!!!!",
                "non adatto a chi non gradisce i prodotti piemontesi, neh?!", null, null, 2, 2));
        return list;
    }
    
    private List<Utente> fakeUtenteRecyclerFill1(){
        List<Utente> list = new ArrayList<>();
        list.add(new Utente(1, "Mario", "Rossi", "Co-Pubblicatore"));
        list.add(new Utente(2, "Alessia", "Bianchi", "Co-Pubblicatore"));
        list.add(new Utente(3, "Carlo", "Martello", "Staff"));
        return list;
    }

    //TODO: modifica e metti menu bar
}