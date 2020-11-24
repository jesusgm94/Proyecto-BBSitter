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
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class InicioCanguroFragment extends Fragment {

    private InicioCanguroViewModel mViewModel;
    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewListaAnuncios, recyclerViewListaDiario;
    private AnunciosAdapter mAdapter, mAdapterDiario;


    private Chip chipNovedades, chipFines, chipDiario;

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

        recyclerViewListaDiario = view.findViewById(R.id.recycler_ListaDiario);
        recyclerViewListaDiario.setLayoutManager(new LinearLayoutManager(getContext()));

        bbdd = FirebaseFirestore.getInstance();

        chipNovedades = view.findViewById(R.id.chip_Novedades);
        chipDiario = view.findViewById(R.id.chip_Diario);
        chipFines = view.findViewById(R.id.chip_Fines);


        //POR DEFECTO SALE ESTA PANTALLA
        Query query = bbdd.collection("anuncios")
                .orderBy("fechaPublicacion", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Anuncio> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Anuncio>()
                .setQuery(query, Anuncio.class).build();
        mAdapter = new AnunciosAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewListaAnuncios.setAdapter(mAdapter);


        /*//Chip Novedades
        chipNovedades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chipNovedades.isChecked())
                {
                    Toast.makeText(getContext(), "chip Novedades", Toast.LENGTH_SHORT).show();

                    Query queryNovedades = bbdd.collection("anuncios").orderBy("fechaPublicacion", Query.Direction.DESCENDING);
                    FirestoreRecyclerOptions<Anuncio> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Anuncio>()
                            .setQuery(queryNovedades, Anuncio.class).build();
                    mAdapter = new AnunciosAdapter(firestoreRecyclerOptions);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewListaAnuncios.setAdapter(mAdapter);
                }

            }
        });

        chipDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chipDiario.isChecked())
                {
                    Toast.makeText(getContext(), "chip Diario", Toast.LENGTH_SHORT).show();

                    Query queryDiario = bbdd.collection("anuncios")
                            .whereEqualTo("tiempo", "Días de diario");

                    FirestoreRecyclerOptions<Anuncio> firestoreRecyclerOptionsDiario = new FirestoreRecyclerOptions.Builder<Anuncio>()
                            .setQuery(queryDiario, Anuncio.class).build();

                    mAdapterDiario = new AnunciosAdapter(firestoreRecyclerOptionsDiario);
                    mAdapterDiario.notifyDataSetChanged();
                    recyclerViewListaDiario.setAdapter(mAdapterDiario);

                }

            }
        });*/

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