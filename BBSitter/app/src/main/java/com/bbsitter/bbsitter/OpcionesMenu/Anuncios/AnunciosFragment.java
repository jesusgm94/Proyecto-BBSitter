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
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.AnunciosAdapter;
import com.bbsitter.bbsitter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AnunciosFragment extends Fragment {

    private AnunciosViewModel anunciosViewModel;

    private Button btnCrearAnuncio;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewListaAnuncios;
    private AnunciosAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        anunciosViewModel = ViewModelProviders.of(this).get(AnunciosViewModel.class);
        View view = inflater.inflate(R.layout.fragment_anuncios, container, false);

        btnCrearAnuncio = view.findViewById(R.id.btnAñadirAnuncio);

        mAuth = FirebaseAuth.getInstance();

        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent crearAnuncio = new Intent(getContext(), CrearAnuncioActivity.class);
                startActivity(crearAnuncio);
            }
        });


        /*String uid = mAuth.getCurrentUser().getUid();

        recyclerViewListaAnuncios = view.findViewById(R.id.recycler_misAnuncios);
        recyclerViewListaAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));
        bbdd = FirebaseFirestore.getInstance();

        Query query = bbdd.collection("anuncios").whereEqualTo("uid", uid);

        FirestoreRecyclerOptions<Anuncio> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Anuncio>()
                .setQuery(query, Anuncio.class).build();

        mAdapter = new AnunciosAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewListaAnuncios.setAdapter(mAdapter);*/

        return view;
    }
}
