package com.example.guitaass.dialogCondivisi.creaEvento;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.R;
import com.example.guitaass.retrofit.eventServer.RetrofitEventClient;
import com.google.gson.Gson;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogCreaEvento extends Dialog {

    private static final String TAG = "DialogCreaEvento";
    private int anno;
    private int mese;
    private int giorno;
    private String nome;
    private String descrizione;
    private String note;
    private boolean streaming;

    private SharedPreferences shpr;

    private long utenteID;
    private long comuneID;

    private boolean modifica = false;

    private Evento evento = null;

    public DialogCreaEvento(@NonNull Context context) {
        super(context);

    }

    public DialogCreaEvento(@NonNull Context context, Evento evento) {
        super(context);
        this.evento = evento;
        modifica = true;
    }

    public DialogCreaEvento(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogCreaEvento(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_crea_evento);

        shpr = PreferenceManager.getDefaultSharedPreferences(getContext());

        this.utenteID = shpr.getLong("utente_id", -1);
        this.comuneID = shpr.getLong("comune_id",-1);
        Log.d(TAG, "onCreate: shpr = " + shpr);
        //tutti gli elementi
        Button ottieniData = findViewById(R.id.nuovo_evento_data);
        Button conferma = findViewById(R.id.crea_nuovo_evento);
        Button annulla = findViewById(R.id.annulla_nuovo_evento);

        EditText nomeInput = findViewById(R.id.nome_evento);
        EditText maxPersoneInput = findViewById(R.id.max_persone);
        EditText descrizioneInput = findViewById(R.id.descrizione);
        EditText notaInput = findViewById(R.id.note);
        Spinner tipoEventoInput = findViewById(R.id.tipo_evento);
        CheckBox streamingBox = findViewById(R.id.streaming);

        if(evento != null){
            TextView titolo = findViewById(R.id.titolo_dialog_crea_evento);
            titolo.setText("Procedura per modificare l'evento: " + evento.getId());
            nomeInput.setText(evento.getNome());
            maxPersoneInput.setText("" + evento.getNumMaxPartecipanti());
            streamingBox.setChecked(evento.isStreaming());
            descrizioneInput.setText(evento.getDescrizione());
            notaInput.setText(evento.getNote());
            if(evento.getData() != null){
                ottieniData.setText(evento.getData().toString());
            }

        }

        //riempimento fake di tipoevento
        ArrayList<String> tipiFake = new ArrayList<>();
        tipiFake.add("Musicale");
        tipiFake.add("Gastronomico");
        tipiFake.add("Festa");
        SpinnerAdapterTipoEvento spinnerAdapter = new SpinnerAdapterTipoEvento(getContext(), tipiFake);
        tipoEventoInput.setAdapter(spinnerAdapter);


        //ottenere la data
        Calendar calendar = Calendar.getInstance();
        anno = calendar.get(Calendar.YEAR);
        mese = calendar.get(Calendar.MONTH);
        giorno = calendar.get(Calendar.DAY_OF_MONTH);
        //bottone che genera un datapicker
        ottieniData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int annoN, int meseN, int giornoN) {
                        ottieniData.setText("" + giornoN + "/" + (meseN+1) + "/" + annoN);
                        anno = annoN;
                        mese = meseN;
                        giorno = giornoN;
                    }
                }, anno, mese, giorno);
                datePickerDialog.show();
            }
        });

        //Bottone annulla
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //Bottone conferma
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //effettuare la richiesta al server
                /*ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setTitle("Creazione");
                progressDialog.setMessage("sto contattando il server per creare il tuo evento");
                progressDialog.show();*/
                java.util.Date dateUtil = calendar.getTime();
                Log.d(TAG, "util.Date: " + dateUtil + "; anno = " + dateUtil.getYear());
                java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
                Log.d(TAG, "sql.Date.toString: " + dateSql.toString() + "; anno = " + dateSql.getYear());
                Log.d(TAG, "y: " + anno + "; m: " + mese + "; d: " + giorno);

                utenteID = shpr.getLong("utente_id", -1);
                comuneID = shpr.getLong("comune_id",-1);

                Log.d(TAG, "utenteID = " + utenteID + "; comuneID = " + comuneID);

                Evento nuovoEvento = new Evento(null, nomeInput.getText().toString(),
                        Integer.parseInt(maxPersoneInput.getText().toString()), 0, streamingBox.isChecked(),
                        descrizioneInput.getText().toString(), notaInput.getText().toString(),
                        null, dateUtil, utenteID, comuneID);
                Call<Evento> call = RetrofitEventClient.getInstance(getContext()).getMyAPI().creaNuovoEvento(nuovoEvento);


                call.enqueue(new Callback<Evento>() {
                    @Override
                    public void onResponse(Call<Evento> call, Response<Evento> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "Nuovo evento creato: " + response.body().getId(), Toast.LENGTH_LONG).show();
                            dismiss();
                        }else{
                            Toast.makeText(getContext(), "C'è stato un errore col server" , Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse: error: " + call.request().body());
                        }

                    }

                    @Override
                    public void onFailure(Call<Evento> call, Throwable t) {
                        Toast.makeText(getContext(), "Non sono riuscito a comunicare col server", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sindaco_iscrizioni, container, false);
        return view;
    }*/
}
