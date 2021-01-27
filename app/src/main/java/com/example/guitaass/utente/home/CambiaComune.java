package com.example.guitaass.utente.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.guitaass.R;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class CambiaComune extends AppCompatActivity implements OnItemSelectedListener {

    private String strMessage;
    private Spinner spinner;
    private String result;
    //JSONObject myData=null;
    TextView textMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_common);
        TextView textVecchioComune = findViewById(R.id.textCambiaComune);
        textMessage = findViewById(R.id.textView);
        String extras = getIntent().getExtras().getString("nome");
        System.out.println(extras);
        strMessage = "http://10.0.2.2:8080/api/v1/utente/nome/"+extras;
        new MyTask().execute();
        /*
        //prendi le informazioni di utente
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String data = extras.getString("utente");
            System.out.println("Ora stampo l'utente --> " +data);
            try {
                myData = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        */
        //SPINNER
        spinner = (Spinner) findViewById(R.id.spinner3);
        /*
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.comuni, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        */
        //BUTTON CHANGE CITY
        Button changeComune = findViewById(R.id.changeComune);
        changeComune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newComune = spinner.getSelectedItem().toString();
            }
        });
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            try {
                url = new URL(strMessage);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String stringBuffer;
                String string = "";
                while ((stringBuffer = bufferedReader.readLine()) != null){
                    string = String.format("%s%s", string, stringBuffer);
                }
                bufferedReader.close();
                result = string;
            } catch (IOException e){
                e.printStackTrace();
                result = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            textMessage.setText(result);
            super.onPostExecute(aVoid);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),item,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
