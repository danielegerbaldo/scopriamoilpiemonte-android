package com.example.guitaass.sindaco.fragmentMioComune.fragmentElencoPersone;

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
        holder.status.setText(utenti.get(position).getStatus());
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
