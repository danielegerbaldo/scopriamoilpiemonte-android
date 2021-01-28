package com.example.guitaass.utente.fragmentIscrizioni;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Utente> utenti;

    private int posizioneEspansa = -1;  //in questa variabile mi salvo qual'è stata l'ultima posizione ad essere stata cliccata

    public RecyclerAdapter(List<Utente> utenti){
        this.utenti = utenti;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_utente_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //Toast.makeText(view.getContext(), "num. utenti = " + utenti.size(), Toast.LENGTH_SHORT).show();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //in questo metodo viene riempita la lista
        holder.codice.setText("5");
        //holder.codice.setText(String.valueOf(utenti.get(position).getId()));
        holder.nome.setText(utenti.get(position).getNome());
        holder.cognome.setText(utenti.get(position).getCognome());
        holder.status.setText(utenti.get(position).getStatus());

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
    }

    @Override
    public int getItemCount() {
        return utenti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //definizmo gli elementi che ci sono nella view di ogni elemento
        TextView codice;
        TextView nome;
        TextView cognome;
        TextView status;

        Button modifica;

        ConstraintLayout extraInfo;
        ConstraintLayout itemBody;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            codice = itemView.findViewById(R.id.codice);
            nome = itemView.findViewById(R.id.nome);
            cognome = itemView.findViewById(R.id.cognome);
            status = itemView.findViewById(R.id.status);
        }

    }
}
