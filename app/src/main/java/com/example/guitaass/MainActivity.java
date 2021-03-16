package com.example.guitaass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guitaass.AsynkTask.RequestNetAsync;
import com.example.guitaass.classiComode.RichiestaLogin;
import com.example.guitaass.sindaco.home.SindacoAiuto;
import com.example.guitaass.sindaco.home.SindacoHome;
import com.example.guitaass.utente.home.UtenteHome;
import com.google.gson.Gson;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences shpr;

    //message handler della risposta
    @SuppressLint("HandlerLeak")
    private final Handler messageHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
        resultReady(msg.getData().getString("RESULT"));
        }
    };

    private void resultReady(String result) {
        Toast.makeText(getApplicationContext(), "HTTP OK", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shpr = PreferenceManager.getDefaultSharedPreferences(this);

        preferenzeCondivise();

        Button registrati = findViewById(R.id.registrati);
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Registrazione.class);
                startActivity(intent);
            }
        });

        Button accedi = findViewById(R.id.accedi);
        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.name);
                EditText password = findViewById(R.id.password);
                //String richiesta = preparaRichiesta();
                String link = "http://" + shpr.getString("IP", "localhost") +
                        ":9090/api/v1/utente/login";
                String body = generaBody(email.getText().toString(), password.getText().toString());
                //genero l'oggetto che si occupa delle richieste asincrone
                RequestNetAsync async = new RequestNetAsync(v.getContext(), messageHandler, "LOGIN", "Connessione..." );
                //avvio una richiesta asincrona passando come parametro il body della richiesta
                async.execute(link, body);
                /*Log.d("Login Activity", "nome = " + nome.getText());
                Log.d("Login Activity", "nome = " + password.getText());
                Log.d("Login Activity", "nome.getText().equals(\"sindaco\") = " + nome.getText().equals("sindaco") );
                Log.d("Login Activity", "password.getText().equals(\"sindaco\") = " + password.getText().equals("sindaco"));*/
                /*if(nome.getText().toString().equals("sindaco") && password.getText().toString().equals("sindaco")){
                    Intent intent = new Intent(v.getContext(), SindacoHome.class);
                    intent.putExtra("Username", "sindaco");
                    intent.putExtra("Email", "sindaco@gmail.com");
                    intent.putExtra("Comune", 1);
                    startActivity(intent);
                }else if(nome.getText().toString().equals("utente") && password.getText().toString().equals("utente")){
                    Intent intent = new Intent(v.getContext(), UtenteHome.class);
                    intent.putExtra("Username", "utente");
                    intent.putExtra("Email", "utente@gmail.com");
                    intent.putExtra("Comune", 2);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Utente sconosciuto!",Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void preferenzeCondivise() {
        if (shpr.getString("IP", "NOTFOUND").equals("NOTFOUND")){
            shpr.edit().putString("IP", "localhost").commit();
        }
    }

    public String generaBody(String email, String password){
        Gson gson = new Gson();
        String json = gson.toJson(new RichiestaLogin(password, email));
        Log.d("JSON", "¡¡¡¡¡¡¡json: " + json);
        return json;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.impostazioni_avanzate:{
                Intent intent = new Intent(getBaseContext(), ImpostazioniAvanzate.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }
}