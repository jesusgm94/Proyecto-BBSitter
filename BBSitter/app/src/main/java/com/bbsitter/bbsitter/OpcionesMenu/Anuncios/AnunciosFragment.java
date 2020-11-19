package com.bbsitter.bbsitter.OpcionesMenu.Anuncios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bbsitter.bbsitter.R;
import com.google.android.material.snackbar.Snackbar;

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

                Snackbar.make(view, "Necesitas una intent para  crear el anuncio", Snackbar.LENGTH_LONG)
                        .setAction("Necesitas hacer el intent para hacer un anuncio", null).show();
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
