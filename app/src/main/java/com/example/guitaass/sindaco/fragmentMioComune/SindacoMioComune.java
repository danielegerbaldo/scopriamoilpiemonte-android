package com.example.guitaass.sindaco.fragmentMioComune;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guitaass.R;

@SuppressLint("ValidFragment")
public class SindacoMioComune extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sindaco_mio_comune, container, false);
        return view;
    }

    //TODO: modifica e metti menu bar
}