package com.example.guitaass.dialogCondivisi.creaEvento;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guitaass.DOM.TipoEvento;
import com.example.guitaass.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapterTipoEvento extends BaseAdapter {
    private List<TipoEvento> listaTipi;
    private Context context;
    LayoutInflater inflater;

    public SpinnerAdapterTipoEvento(Context context, List<TipoEvento> listaTipi){
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
        tipo.setText(listaTipi.get(position).getNome());
        return convertView;
    }

    public void aggiorna(List<TipoEvento> listaTipi){
        this.listaTipi = listaTipi;
        notifyDataSetChanged();
    }

    public int getPositionOf(TipoEvento tipoEvento){
        Log.d("SpinnerAdapter", "getPositionOf: id= " + tipoEvento.getId());
        for(int i = 0; i < listaTipi.size(); i++){
            if(listaTipi.get(i).getId() == tipoEvento.getId()){
                Log.d("SpinnerAdapter", "getPositionOf: comparazion = " + (listaTipi.get(i).getId() == tipoEvento.getId()));
                return i;
            }
        }
        return -1;
    }


    /*public TipoEvento getItemSelected(){
        return null;
    }*/
}
