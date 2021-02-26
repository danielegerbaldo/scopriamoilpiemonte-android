package com.example.guitaass.dialogCondivisi.creaEvento;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.guitaass.R;

import java.util.ArrayList;
import java.util.Date;

public class DialogEvento extends Dialog {

    private int anno;
    private int mese;
    private int giorno;
    private String nome;
    private String descrizione;
    private String note;
    private boolean streaming;

    public DialogEvento(@NonNull Context context) {
        super(context);

    }

    public DialogEvento(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogEvento(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_crea_evento);
        //tutti gli elementi
        Button ottieniData = findViewById(R.id.nuovo_evento_data);
        Button conferma = findViewById(R.id.crea_nuovo_evento);
        Button annulla = findViewById(R.id.annulla_nuovo_evento);

        EditText nomeInput = findViewById(R.id.nome_evento);
        EditText maxPersoneInput = findViewById(R.id.max_persone);
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
                progressDialog.show();

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
