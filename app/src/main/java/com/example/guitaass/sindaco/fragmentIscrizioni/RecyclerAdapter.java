package com.example.guitaass.sindaco.fragmentIscrizioni;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Evento;
import com.example.guitaass.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Evento> eventi;

    private int posizioneEspansa = -1;  //in questa variabile mi salvo qual'è stata l'ultima posizione ad essere stata cliccata


    public RecyclerAdapter(List<Evento> eventi){
        this.eventi = eventi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_evento_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //Toast.makeText(view.getContext(), "num. eventi = " + eventi.size(), Toast.LENGTH_SHORT).show();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //in questo metodo viene riempita la lista
        holder.nomeEvento.setText(eventi.get(position).getNome());
        holder.comune.setText("" +  eventi.get(position).getComune());
        holder.descrizione.setText(eventi.get(position).getDescrizione());
        holder.note.setText(eventi.get(position).getNote());
        holder.partecipanti.setText("" + eventi.get(position).getPartecipanti());
        holder.indirizzo.setText("boh");
        holder.streaming.setText("" + eventi.get(position).isStreaming());
        holder.data.setText("" + eventi.get(position).getData());
        holder.id.setText("" + eventi.get(position).getId());

        //rende visibile o meno la parte extra
        if(position == posizioneEspansa){
            holder.extraInfo.setVisibility(View.VISIBLE);
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
        TextView partecipanti;
        TextView indirizzo;
        TextView streaming;
        TextView data;
        TextView id;

        Button positivo;
        Button negativo;
        Button modifica;

        ConstraintLayout extraInfo;
        ConstraintLayout itemBody;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nomeEvento = itemView.findViewById(R.id.nome_evento);
            comune = itemView.findViewById(R.id.comune);
            descrizione = itemView.findViewById(R.id.descrizione);
            note = itemView.findViewById(R.id.note);
            partecipanti = itemView.findViewById(R.id.partecipanti);
            indirizzo = itemView.findViewById(R.id.indirizzo);
            streaming = itemView.findViewById(R.id.streaming);
            data = itemView.findViewById(R.id.data);
            id = itemView.findViewById(R.id.id);

            positivo = itemView.findViewById(R.id.button_pos);
            negativo = itemView.findViewById(R.id.button_neg);
            modifica = itemView.findViewById(R.id.button_mod);

            extraInfo = itemView.findViewById(R.id.extra_info);
            itemBody = itemView.findViewById(R.id.item_body);

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
}
