package com.example.guitaass.dialogCondivisi.creaSondaggio;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.guitaass.R;
import com.example.guitaass.dialogCondivisi.creaEvento.SpinnerAdapterTipoEvento;

import java.util.ArrayList;

public class DialogCreaSondaggio extends Dialog {
    private int anno;
    private int mese;
    private int giorno;
    private String nome;
    private String descrizione;
    private String note;
    private boolean streaming;

    public DialogCreaSondaggio(@NonNull Context context) {
        super(context);
    }

    public DialogCreaSondaggio(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogCreaSondaggio(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_crea_sondaggio);

        //tutti gli elementi
        Button ottieniData = findViewById(R.id.nuovo_sondaggio_data);
        Button conferma = findViewById(R.id.crea_nuovo_sondaggio);
        Button annulla = findViewById(R.id.annulla_nuovo_sondaggio);

        EditText nomeInput = findViewById(R.id.nome_sondaggio);
        EditText maxPersoneInput = findViewById(R.id.min_persone);
        CheckBox streaming = findViewById(R.id.streaming);
        EditText descrizioneInput = findViewById(R.id.descrizione);
        EditText notaInput = findViewById(R.id.note);
        Spinner tipoEventoInput = findViewById(R.id.tipo_evento);


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
                    public void onDateSet(DatePicker view, int anno, int mese, int giorno) {
                        ottieniData.setText("" + giorno + "/" + (mese+1) + "/" + anno);
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
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setTitle("Creazione");
                progressDialog.setMessage("sto contattando il server per creare il tuo sondaggio");
                progressDialog.show();

            }
        });
    }
}
