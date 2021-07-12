package com.example.guitaass.sindaco.fragmentMioComune.fragmentElencoPersone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.R;

import java.util.List;

public class PersoneRecyclerAdapter extends RecyclerView.Adapter<PersoneRecyclerAdapter.ViewHolder> {

    List<Utente> utenti;

    public PersoneRecyclerAdapter(List<Utente> utenti) {
        this.utenti = utenti;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_persona_item, parent, false);
        ViewHolder  holder = new ViewHolder(view);
        //Toast.makeText(view.getContext(), "n. Utenti =" + utenti.size(), Toast.LENGTH_SHORT).show();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nome.setText(utenti.get(position).getNome());
        holder.cognome.setText(utenti.get(position).getCognome());
        holder.status.setText(utenti.get(position).getRuoli().get(0).getAuthority());

        //caso base, in seguito si valuterà se ci sono apltre opzioni di visualizzazione
        holder.positivo.setText("promuovi");
        holder.negativo.setText("espelli");

        holder.positivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //devo mostrare un dialog in cui l'utente con uno spinner sceglie il nuovo ruolo al personale
                Toast.makeText(v.getContext(), "Funzione non ancora sviluppata", Toast.LENGTH_SHORT).show();
            }
        });

        holder.negativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //devo mostrare un dialog di conferma
                AlertDialog.Builder confermaEspulsione = new AlertDialog.Builder(v.getContext());
                confermaEspulsione.setTitle("Conferma Espulsione");
                confermaEspulsione.setMessage("Sei sicuro di voler espellere questo membro dal tuo comune?");
                //configurazione bottone positivo
                confermaEspulsione.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(), "Funzione non ancora sviluppata", Toast.LENGTH_SHORT).show();
                    }
                });
                //configurazione bottone negativo
                confermaEspulsione.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                confermaEspulsione.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return utenti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //definizione degli elementi nella view di ogni elemento
        TextView nome;
        TextView cognome;
        TextView status;

        Button positivo;
        Button negativo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            cognome = itemView.findViewById(R.id.cognome);
            status = itemView.findViewById(R.id.status);

            positivo = itemView.findViewById(R.id.btn_positivo);
            negativo = itemView.findViewById(R.id.btn_negativo);
        }
    }
}
