package com.example.guitaass.registrazione;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guitaass.R;
import com.example.guitaass.retrofit.municipalityClient.Comune;

import java.util.List;

public class SpinnerAdapterComuni extends BaseAdapter {

    private List<Comune> listaComuni;
    private Context context;
    LayoutInflater inflater;

    public SpinnerAdapterComuni(List<Comune> listaComuni, Context context) {
        this.listaComuni = listaComuni;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaComuni.size();
    }

    @Override
    public Comune getItem(int position) {
        return listaComuni.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.text_spinner_element, null);
        TextView tipo = convertView.findViewById(R.id.testo);
        tipo.setText(listaComuni.get(position).getNome());
        return convertView;
    }


}
