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
import android.util.ArraySet;
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
import com.example.guitaass.DOM.TipoEvento;
import com.example.guitaass.R;
import com.example.guitaass.retrofit.eventServer.RetrofitEventClient;
import com.example.guitaass.retrofit.tipoEventoServer.RetrofitTipoEventoClient;
import com.google.gson.Gson;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        Log.d(TAG, "DialogCreaEvento: creato con evento -> modifica = " + modifica);
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

        TipoEvento tipoEventoSelezionato = null;    //rappresenta il tipo di evento selezionato dallo spinner

        //creo l'adapter impostandogli inizialmente una lista vuota di tipo di evento
        SpinnerAdapterTipoEvento spinnerAdapter  = new SpinnerAdapterTipoEvento(getContext(), new ArrayList<>() );
        //setto l'adapter allo spinner
        tipoEventoInput.setAdapter(spinnerAdapter);
        //mostro un progress dialog
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Caricamento tipi di evento");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("caricamento della lista dei tipi di evento dal server");
        progressDialog.show();
        //chiamata al server che mi fa ottenere l'elenco dei tipi di evento
        Call<List<TipoEvento>> call = RetrofitTipoEventoClient.getInstance(getContext()).getMyAPI().ottieniListaTipiEvento();
        Log.d(TAG, "richiesta lista tipi evento: " + call.request());
        call.enqueue(new Callback<List<TipoEvento>>() {
            @Override
            public void onResponse(Call<List<TipoEvento>> call, Response<List<TipoEvento>> response) {
                //aggiorno l'elenco di tipi dello spinner
                List<TipoEvento> tipiEventi = new ArrayList<>();
                if(response.body() != null){
                    tipiEventi = response.body();
                }
                Toast.makeText(getContext(), "Lista tipi size: " + tipiEventi.size() , Toast.LENGTH_LONG).show();
                spinnerAdapter.aggiorna(tipiEventi);
                progressDialog.dismiss();
                if(evento != null){
                    //settare il valore di default dello spinner
                    tipoEventoInput.setSelection(spinnerAdapter.getPositionOf(evento.getTipoEvento()));
                }
            }

            @Override
            public void onFailure(Call<List<TipoEvento>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

        //quando si vuole modificare un evento, in questo if viene riempito il dialog con i valori dell'evento da modificare
        if(evento != null){
            TextView titolo = findViewById(R.id.titolo_dialog_crea_evento);
            titolo.setText("Procedura per modificare l'evento: " + evento.getId());
            nomeInput.setText(evento.getNome());
            maxPersoneInput.setText("" + evento.getNumMaxPartecipanti());
            streamingBox.setChecked(evento.isStreaming());
            descrizioneInput.setText(evento.getDescrizione());
            notaInput.setText(evento.getNote());
            if(evento.getData() != null){
                ottieniData.setText(evento.getData().getDay() + "/" + evento.getData().getMonth() + "/" + (evento.getData().getYear() + 1900));
            }

        }


        //ottenere la data
        Calendar calendar = Calendar.getInstance();
        anno = calendar.get(Calendar.YEAR);
        mese = calendar.get(Calendar.MONTH);
        giorno = calendar.get(Calendar.DAY_OF_MONTH);
        //Date data;
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
                        calendar.set(anno,mese,giorno);
                        //data = Date.valueOf()
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
                //java.util.Date dateUtil = calendar.getTime();
                java.util.Date dateUtil = calendar.getTime();
                        //Date.valueOf(anno + "/" + mese + "/" + giorno);
                Log.d(TAG, "util.Date: " + dateUtil + "; anno = " + dateUtil.getYear() + "; giorno = " + dateUtil.getDay());
                //java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
                //Log.d(TAG, "sql.Date.toString: " + dateSql.toString() + "; anno = " + dateSql.getYear());
                Log.d(TAG, "y: " + anno + "; m: " + mese + "; d: " + giorno);

                utenteID = shpr.getLong("utente_id", -1);
                comuneID = shpr.getLong("comune_id",-1);

                Log.d(TAG, "utenteID = " + utenteID + "; comuneID = " + comuneID);

                /*Evento nuovoEvento = new Evento(null, nomeInput.getText().toString(),
                        Integer.parseInt(maxPersoneInput.getText().toString()), 0, streamingBox.isChecked(),
                        descrizioneInput.getText().toString(), notaInput.getText().toString(),
                        (TipoEvento) tipoEventoInput.getSelectedItem(), dateUtil, utenteID, comuneID);*/
                //tipoEventoSelezionato = (TipoEvento) tipoEventoInput.getSelectedItem();
                TipoEvento tipoEvento = (TipoEvento) tipoEventoInput.getSelectedItem();
                //tipoEvento.setId(null);
                Evento nuovoEvento = new Evento(nomeInput.getText().toString(), Integer.parseInt(maxPersoneInput.getText().toString()), 0, streaming,
                        descrizioneInput.getText().toString(), notaInput.getText().toString(), tipoEvento, dateUtil,
                        (shpr.getLong("utente_id", 0)),
                        (shpr.getLong("comune_id", 0)), "",
                        new ArraySet<Long>(), 0.0, 0.0, 0.0);
                Log.d(TAG, "onClick: evento creato: " + nuovoEvento.toString());
                Log.d(TAG, "onClick: evento creato: " + new Gson().toJson(nuovoEvento));

                if(modifica){
                    //nel caso in cui il dialog serva per modificare un evento bisogna fare una certa chiamata al server
                    Log.d(TAG, "onClick: modifica = " + modifica);
                    Call<Evento> call = RetrofitEventClient
                            .getInstance(getContext())
                            .getMyAPI()
                            .modificaEvento(evento.getId(), nuovoEvento, "Bearer " + shpr.getString("token", ""));
                    call.enqueue(new Callback<Evento>() {
                        @Override
                        public void onResponse(Call<Evento> call, Response<Evento> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getContext(), "Evento modificato con successo: " + response.body().getId(), Toast.LENGTH_LONG).show();
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

                }else{
                    //nel caso in cui il dialog serva per creare un nuovo evento
                    Call<Evento> call = RetrofitEventClient
                            .getInstance(getContext())
                            .getMyAPI()
                            .creaNuovoEvento(nuovoEvento, "Bearer " + shpr.getString("token", ""));

                    Log.d(TAG, "invio richiesta: body: " + new Gson().toJson(call.request().body()));
                    call.enqueue(new Callback<Evento>() {
                        @Override
                        public void onResponse(Call<Evento> call, Response<Evento> response) {
                            Log.d(TAG, "invio richiesta: body: " + new Gson().toJson(call.request().body()));

                            if(response.isSuccessful()){
                                Toast.makeText(getContext(), "Nuovo evento creato: " + response.body().getId(), Toast.LENGTH_LONG).show();
                                Log.d(TAG, "onResponse; evento creato: " + response.body().toString());
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
            }
        });
    }

}
