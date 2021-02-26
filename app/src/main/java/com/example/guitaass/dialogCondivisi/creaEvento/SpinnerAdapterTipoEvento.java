package com.example.guitaass.dialogCondivisi.creaEvento;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guitaass.R;

import java.util.ArrayList;

public class SpinnerAdapterTipoEvento extends BaseAdapter {
    private ArrayList<String> listaTipi;
    private Context context;
    LayoutInflater inflater;

    public SpinnerAdapterTipoEvento(Context context, ArrayList<String> listaTipi){
        this.context = context;
        this.listaTipi = listaTipi;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaTipi.size();
    }

    @Override
    public Object getItem(int position) {
        return listaTipi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.text_spinner_element, null);
        TextView tipo = convertView.findViewById(R.id.testo);
        tipo.setText(listaTipi.get(position));
        return convertView;
    }
}
