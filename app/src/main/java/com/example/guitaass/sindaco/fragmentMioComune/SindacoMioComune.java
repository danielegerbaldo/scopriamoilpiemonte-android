package com.example.guitaass.sindaco.fragmentMioComune;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;
import com.example.guitaass.fragmentCondivisi.fragmentEventi.FragmentEventi;
import com.example.guitaass.fragmentCondivisi.fragmentEventi.FragmentEventiRecyclerAdapter;
import com.example.guitaass.retrofit.eventServer.RetrofitEventClient;
import com.example.guitaass.sindaco.fragmentMioComune.fragmentElencoPersone.FragmentElencoPersone;
import com.example.guitaass.sindaco.fragmentMioComune.fragmentInfo.FragmentInfo;
import com.example.guitaass.sindaco.fragmentMioComune.fragmentSegnalazioni.FragmentSegnalazioni;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.getIntent;

@SuppressLint("ValidFragment")
public class SindacoMioComune extends Fragment {

    private SharedPreferences shpr;

    private String TAG = "SindacoMioComune";

    private Context context;

    private String email = "";

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
        context = view.getContext();
        shpr = PreferenceManager.getDefaultSharedPreferences(context);

        //Log.d("FragmentMioComune", "***********context = " + context);

        //imposto il tab che si vede di default
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentEventi fragment = new FragmentEventi(1, shpr.getLong("utente_id", -1), shpr.getLong("comune_id", -1), 3,true);
        fragmentTransaction.replace(R.id.fragment2, fragment, "EventiComune").addToBackStack(null).commit();
        //aggiornaElencoEventiComune(fragment);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selezionato = tab.getPosition();
                //Toast.makeText(context, "tab " + selezionato, Toast.LENGTH_SHORT).show();
                switch (selezionato){
                    case 0:{    //eventi
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentEventi fragment = new FragmentEventi(1, shpr.getLong("utente_id", -1), shpr.getLong("comune_id", -1),3, true);
                        fragmentTransaction.replace(R.id.fragment2, fragment, "EventiComune").addToBackStack(null).commit();
                        //aggiornaElencoEventiComune(fragment);
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
    
    private List<Utente> fakeUtenteRecyclerFill1(){
        List<Utente> list = new ArrayList<>();
        /*list.add(new Utente(1, "Mario", "Rossi", "Co-Pubblicatore"));
        list.add(new Utente(2, "Alessia", "Bianchi", "Co-Pubblicatore"));
        list.add(new Utente(3, "Carlo", "Martello", "Staff"));*/
        return list;
    }

    //TODO: modifica e metti menu bar
}