package com.bbsitter.bbsitter.OpcionesMenuCanguro.Inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.AnunciosAdapter;
import com.bbsitter.bbsitter.Clases.Anuncio;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class InicioCanguroFragment extends Fragment {

    private InicioCanguroViewModel mViewModel;
    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewListaAnuncios;
    private AnunciosAdapter mAdapter;

    public static InicioCanguroFragment newInstance() {
        return new InicioCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.inicio_canguro_fragment, container, false);


        recyclerViewListaAnuncios = view.findViewById(R.id.recycler_ListaAnuncios);
        recyclerViewListaAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));
        bbdd = FirebaseFirestore.getInstance();

        Query query = bbdd.collection("anuncios");

        FirestoreRecyclerOptions<Anuncio> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Anuncio>()
                .setQuery(query, Anuncio.class).build();

        mAdapter = new AnunciosAdapter(firestoreRecyclerOptions);


         mAdapter.notifyDataSetChanged();

        recyclerViewListaAnuncios.setAdapter(mAdapter);

        /*mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Le has dado a un item", Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InicioCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();

    }

    public void onStop() {

        super.onStop();
        mAdapter.stopListening();
    }

}