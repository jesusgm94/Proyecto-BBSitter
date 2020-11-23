package com.bbsitter.bbsitter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CrearAnuncioFragment extends Fragment {

    private CrearAnuncioViewModel mViewModel;
    Button btnCrearAnuncio;


    public static CrearAnuncioFragment newInstance() {
        return new CrearAnuncioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.crear_anuncio_fragment, container, false);

        btnCrearAnuncio = view.findViewById(R.id.btnAñadirAnuncio);

        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CrearAnuncioViewModel.class);
        // TODO: Use the ViewModel
    }


}