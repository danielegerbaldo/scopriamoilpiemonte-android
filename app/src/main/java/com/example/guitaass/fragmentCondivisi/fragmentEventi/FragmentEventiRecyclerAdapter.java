package com.example.guitaass.fragmentCondivisi.fragmentEventi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.R;
import com.example.guitaass.dialogCondivisi.creaEvento.DialogCreaEvento;
import com.example.guitaass.retrofit.eventServer.IscriviEvento;
import com.example.guitaass.retrofit.eventServer.RetrofitEventClient;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEventiRecyclerAdapter extends RecyclerView.Adapter<FragmentEventiRecyclerAdapter.ViewHolder> {
    private String TAG = "FragmentEventiRecyclerAdapter";

    private List<Evento> eventi;
    private SharedPreferences shpr;

    private int posizioneEspansa = -1;  //in questa variabile mi salvo qual'è stata l'ultima posizione ad essere stata cliccata

    private int visualizzazioneBottoni = 0;     /*
                                            Questa variabile rappresenta quale visualizzazione devo realizzare dei bottoni:
                                            1 = visualizzo tutto: testi = modifica, conferma, elimina
                                            2 = solo negativo = disdici prenotazione
                                            3 = negativo e modifica = modifica, elimina
                                            4 = solo positivo = prenota
                                            */

    private FragmentEventi chiamante;

    private GoogleMap map;

    private Context context;
    private View view;


    public FragmentEventiRecyclerAdapter(List<Evento> eventi, int visualizzazioneBottoni, FragmentEventi chiamante){
        this.eventi = eventi;
        this.visualizzazioneBottoni = visualizzazioneBottoni;
        this.chiamante = chiamante;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_evento_item, parent, false);
        shpr = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        ViewHolder holder = new ViewHolder(view);
        context = view.getContext();
        this.view = view;
        //Toast.makeText(view.getContext(), "num. eventi = " + eventi.size(), Toast.LENGTH_SHORT).show();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //in questo metodo viene riempita la lista
        Evento evento = eventi.get(position);
        holder.nomeEvento.setText(evento.getNome());
        holder.comune.setText("" +  evento.getComune());
        holder.descrizione.setText(evento.getDescrizione());
        holder.note.setText(evento.getNote());
        if(evento.getTipoEvento() != null){
            holder.tipoEvento.setText(evento.getTipoEvento().getNome());
        }else{
            holder.tipoEvento.setText("null");
        }
        holder.partecipanti.setText("" + evento.getPartecipanti());
        holder.posti.setText("" + evento.getNumMaxPartecipanti());
        holder.indirizzo.setText("boh");
        holder.streaming.setText("" + evento.isStreaming());
        /*"" + evento.getData().getDay() + "/" + evento.getData().getMonth() + "/" + (evento.getData().getYear() + 1900)*/
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.data.setText(formatter.format(evento.getData()));
        holder.id.setText("" + evento.getId());




        //rende visibile o meno la parte extra
        if(position == posizioneEspansa){
            holder.extraInfo.setVisibility(View.VISIBLE);

            //posso permettermi di definire qua il comportamento di modifica in quato è uguale in tutti i posti in cui compare
            holder.modifica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogCreaEvento modificaEvento = new DialogCreaEvento(v.getContext(), evento);
                    modificaEvento.show();
                    Log.d(TAG, "cliccato modifica, prima della chiamata aggiorna: ");
                    modificaEvento.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            chiamante.aggiornaEventiAdapter();
                        }
                    });

                    Log.d(TAG, "cliccato modifica, dopo della chiamata aggiorna: ");
                }
            });

            //imposto setto quali bottoni sono visibili
            switch (visualizzazioneBottoni){
                case 1:{    //1 = visualizzo tutto: testi = modifica, conferma, elimina
                    /*
                    //questo verrà solo usato con le proposte di evento
                    holder.positivo.setVisibility(View.GONE);
                    holder.positivo.setText("conferma");

                    holder.negativo.setVisibility(View.VISIBLE);
                    holder.negativo.setText("elimina");

                    holder.modifica.setVisibility(View.VISIBLE);
                    holder.modifica.setText("modifica");

                    //imposto cosa fa il tasto negativo, ovvero quello che elimina l'evento

                    holder.negativo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(v.getContext(), "hai cliccato elimina", Toast.LENGTH_SHORT).show();
                            //prima di cancellare chiedo conferma dell'azione con un alertdialog
                            AlertDialog.Builder confermaCancellazione = new AlertDialog.Builder(v.getContext());
                            confermaCancellazione.setCancelable(false);
                            confermaCancellazione.setTitle("Cancellazione");
                            confermaCancellazione.setMessage("sicuro di voler cancellare l'evento dal tuo comune?");
                            confermaCancellazione.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(v.getContext(), "l'evento è stato cancellato", Toast.LENGTH_SHORT).show();
                                }
                            });
                            confermaCancellazione.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            confermaCancellazione.show();
                        }
                    });*/
                    break;
                }

                case 2:{    //2 = solo negativo = disdici prenotazione
                    holder.positivo.setVisibility(View.GONE);
                    holder.negativo.setVisibility(View.VISIBLE);
                    holder.negativo.setText("disdici prenotazione");
                    holder.modifica.setVisibility(View.GONE);

                    //imposto il comportamento del tasto per disdire le prenotazioni
                    holder.negativo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //prima di disdire la prenotazione chiedo conferma tramite un dialog
                            AlertDialog.Builder confermaDisdici = new AlertDialog.Builder(v.getContext());
                            confermaDisdici.setTitle("Disdici prenotazione");
                            confermaDisdici.setMessage("Sei sicuro di voler disdire la tua prenotazione all'evento?");
                            confermaDisdici.setCancelable(false);
                            confermaDisdici.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    disdiciPrenotazione(v.getContext(), position);
                                }
                            });
                            confermaDisdici.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            confermaDisdici.show();
                        }
                    });
                    break;
                }

                case 3:{    //3 = negativo e modifica = modifica, elimina
                    holder.positivo.setVisibility(View.GONE);
                    holder.negativo.setVisibility(View.VISIBLE);
                    holder.modifica.setVisibility(View.VISIBLE);
                    holder.negativo.setText("Elimina");
                    holder.modifica.setText("Modifica");

                    holder.negativo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder confermaDisdici = new AlertDialog.Builder(v.getContext());
                            confermaDisdici.setTitle("Elimina evento");
                            confermaDisdici.setMessage("Sei sicuro di voler eliminare questo evento?");
                            confermaDisdici.setCancelable(false);
                            confermaDisdici.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminaEvento(v.getContext(), position);
                                }
                            });
                            confermaDisdici.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            confermaDisdici.show();
                        }
                    });
                    break;
                }

                case 4:{        //4 = solo positovo per prenotare l'evento
                    holder.positivo.setVisibility(View.VISIBLE);
                    holder.negativo.setVisibility(View.GONE);
                    holder.modifica.setVisibility(View.GONE);
                    holder.positivo.setText("Prenota");
                    /* controllo della disponibilità di posti;
                    se non ci sono più posti disponibili il tasto viene disattivato e appare un toast
                    altrimenti è possibile prenotare
                    */
                    if (evento.getPartecipanti() >= evento.getNumMaxPartecipanti()) {
                        holder.positivo.setBackgroundColor(Color.GRAY);
                        holder.positivo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "non ci sono posti disponibili!" , Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        holder.positivo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prenota(v.getContext(), position);
                            }
                        });
                    }
                    break;
                }
            }
        }else {
            holder.extraInfo.setVisibility(View.GONE);
        }

        //cliccando il corpo modifico la variabile posizioneEspansa e notifico che bisogna aggiornare la view
        holder.itemBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posizioneEspansa >= 0){
                    int prev = posizioneEspansa;
                    if(prev == position && holder.extraInfo.getVisibility() == View.VISIBLE){
                        holder.extraInfo.setVisibility(View.GONE);
                    }else{
                        notifyItemChanged(prev);
                        posizioneEspansa = position;
                        notifyItemChanged(posizioneEspansa);
                    }
                }else{
                    posizioneEspansa = position;
                    notifyItemChanged(posizioneEspansa);
                }
            }
        });

        //rendo cliccabile il corpo dell'elemento nella lista
    }

    @Override
    public int getItemCount() {
        return eventi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //definizmo gli elementi che ci sono nella view di ogni elemento
        TextView nomeEvento;
        TextView comune;
        TextView descrizione;
        TextView note;
        TextView tipoEvento;
        TextView partecipanti;
        TextView posti;
        TextView indirizzo;
        TextView streaming;
        TextView data;
        TextView id;

        Button positivo;
        Button negativo;
        Button modifica;

        ConstraintLayout extraInfo;
        ConstraintLayout itemBody;

        MapView mapView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nomeEvento = itemView.findViewById(R.id.nome_evento);
            comune = itemView.findViewById(R.id.comune);
            descrizione = itemView.findViewById(R.id.descrizione);
            note = itemView.findViewById(R.id.note);
            tipoEvento = itemView.findViewById(R.id.tipo_evento);
            partecipanti = itemView.findViewById(R.id.partecipanti);
            posti = itemView.findViewById(R.id.posti);
            indirizzo = itemView.findViewById(R.id.indirizzo);
            streaming = itemView.findViewById(R.id.streaming);
            data = itemView.findViewById(R.id.data);
            id = itemView.findViewById(R.id.id);

            positivo = itemView.findViewById(R.id.button_pos);
            negativo = itemView.findViewById(R.id.button_neg);
            modifica = itemView.findViewById(R.id.button_mod);

            extraInfo = itemView.findViewById(R.id.extra_info);
            itemBody = itemView.findViewById(R.id.item_body);

            mapView = itemView.findViewById(R.id.web_view);

            //mapView.onCreate(new Bundle());
            //mapView.getMapAsync(view);

            descrizione.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //si crea un alert che semplicemente fa vedere il contenuto della descrizione
                    AlertDialog dialogDescrizione;
                    AlertDialog.Builder dialogDescrizioneBuilder = new AlertDialog.Builder(itemView.getContext());
                    dialogDescrizioneBuilder.setMessage(descrizione.getText());

                    dialogDescrizioneBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    dialogDescrizione = dialogDescrizioneBuilder.create();
                    dialogDescrizione.setTitle("Descizione completa evento");
                    dialogDescrizione.show();

                }
            });

            note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //si crea un alert che semplicemente fa vedere il contenuto delle note
                    AlertDialog dialogNote;
                    AlertDialog.Builder dialogNoteBuilder = new AlertDialog.Builder(itemView.getContext());
                    dialogNoteBuilder.setMessage(note.getText());

                    dialogNoteBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    dialogNote  = dialogNoteBuilder.create();
                    dialogNote.setTitle("Note complete evento");
                    dialogNote.show();
                }
            });
        }

    }

    public List<Evento> getEventi() {
        return eventi;
    }

    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }

    private void prenota (Context context, int position){
        long eventoID  = eventi.get(position).getId();
        long utenteID = shpr.getLong("utente_id", -1);
        Map<String, Long> body = new HashMap<String, Long>();
        body.put("utente_id", utenteID);
        body.put("evento_id", eventoID);

        IscriviEvento iscrizioneRequest =  new IscriviEvento(eventoID, utenteID);
        Toast.makeText(context, "eventoId = " + iscrizioneRequest.getEvento() + " utenteId = " + iscrizioneRequest.getUtente(), Toast.LENGTH_SHORT).show();

        Call<Evento> call = RetrofitEventClient
                .getInstance(context)
                .getMyAPI()
                .prenotaEvento(iscrizioneRequest, "Bearer " + shpr.getString("token", ""));
        call.enqueue(new Callback<Evento>() {
            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Iscritto all'evento " + response.body().getId(), Toast.LENGTH_SHORT).show();
                    //eventi.remove(position);
                    //notifyItemRemoved(position);
                    chiamante.aggiornaEventiAdapter();
                }else{
                    try {
                        Toast.makeText(context, "errore: " + response.errorBody().string().toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(context, "errore: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
                Toast.makeText(context, "non è stato possibile contattare il server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void disdiciPrenotazione(Context context, int position){
        long eventoID  = eventi.get(position).getId();
        long utenteID = shpr.getLong("utente_id", -1);
        Map<String, Long> body = new HashMap<String, Long>();
        body.put("utente_id", utenteID);
        body.put("evento_id", eventoID);
        //IscrizioneRequest iscrizione = new IscrizioneRequest(eventoID, utenteID);
        Call<Map<String, String>> call = RetrofitEventClient
                .getInstance(context)
                .getMyAPI()
                .disdiciPrenotazione(body, "Bearer " + shpr.getString("token", "") );
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "" + response.body().get("messaggio"), Toast.LENGTH_SHORT).show();
                    //eventi.remove(position);
                    //notifyItemRemoved(position);
                    chiamante.aggiornaEventiAdapter();
                }else{
                    try {
                        Toast.makeText(context, "errore: " + response.errorBody().string().toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(context, "errore: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(context, "non è stato possibile contattare il server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminaEvento(Context context, int position) {
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!eliminaEvento: sono passato qua ");
        long eventoID = eventi.get(position).getId();
        Call<String> call = RetrofitEventClient
                .getInstance(context)
                .getMyAPI()
                .eliminaEvento(eventoID, "Bearer " + shpr.getString("token", ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "Elimina evento: codice risposta = " + response.code());
                if(response.code() == 200){
                    Toast.makeText(context, "" + response.body(), Toast.LENGTH_SHORT).show();
                    //eventi.remove(position);
                    //notifyItemRemoved(position);
                    chiamante.aggiornaEventiAdapter();

                }else{
                    try {
                        Toast.makeText(context, "errore: " + response.errorBody().string().toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(context, "errore: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //TODO: BUG LIBRERIA, scoprire perché va qui anche se il server da ok
                //Toast.makeText(context, "non è stato possibile contattare il server", Toast.LENGTH_SHORT).show();
                chiamante.aggiornaEventiAdapter();
            }
        });
    }

    public void aggiornaLista(List<Evento> nuovaLista){
        Log.d(TAG, "aggiornaLista: numero vecchi eventi: " + eventi.size());
        Log.d(TAG, "aggiornaLista: numero nuovi eventi: " + nuovaLista.size());
        eventi = nuovaLista;
        notifyDataSetChanged();
    }
}
