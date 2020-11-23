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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.AnunciosAdapter;
import com.bbsitter.bbsitter.Clases.Anuncio;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AnunciosFragment extends Fragment {

    private AnunciosViewModel anunciosViewModel;

    private Button btnCrearAnuncio;
    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewListaAnuncios;
    private AnunciosAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        anunciosViewModel = ViewModelProviders.of(this).get(AnunciosViewModel.class);
        View view = inflater.inflate(R.layout.fragment_anuncios, container, false);

        btnCrearAnuncio = view.findViewById(R.id.btnAñadirAnuncio);

        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent crearAnuncio = new Intent(getContext(), CrearAnuncioActivity.class);
                startActivity(crearAnuncio);
            }
        });

        return view;
    }
}
