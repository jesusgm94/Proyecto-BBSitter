package com.bbsitter.bbsitter.OpcionesMenu.Anuncios;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bbsitter.bbsitter.R;

public class AnunciosFragment extends Fragment {

    private AnunciosViewModel anunciosViewModel;

    Button btnCrearAnuncio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        anunciosViewModel =
                ViewModelProviders.of(this).get(AnunciosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_anuncios, container, false);

        btnCrearAnuncio = root.findViewById(R.id.btnAñadirAnuncio);

        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent crearAnuncio = new Intent(getContext(), CrearAnuncioActivity.class);
                startActivity(crearAnuncio);
            }
        });

        /*
        final TextView textView = root.findViewById(R.id.text_slideshow);
        favoritosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }
}
