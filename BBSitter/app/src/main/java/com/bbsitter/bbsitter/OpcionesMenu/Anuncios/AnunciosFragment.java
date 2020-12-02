package com.bbsitter.bbsitter.OpcionesMenu.Anuncios;

import android.content.DialogInterface;
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

import com.bbsitter.bbsitter.Adaptadores.MisAnunciosAdapter;
import com.bbsitter.bbsitter.Clases.Anuncio;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class AnunciosFragment extends Fragment {

    private AnunciosViewModel anunciosViewModel;

    private Button btnCrearAnuncio;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewMisAnuncios;
    private MisAnunciosAdapter mAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        anunciosViewModel = ViewModelProviders.of(this).get(AnunciosViewModel.class);
        View view = inflater.inflate(R.layout.fragment_anuncios, container, false);


        btnCrearAnuncio = view.findViewById(R.id.btnAñadirAnuncio);

        mAuth = FirebaseAuth.getInstance();

        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CrearAnuncioFragment crearAnunciosFragment = new CrearAnuncioFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, crearAnunciosFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        String uid = mAuth.getCurrentUser().getUid();


        recyclerViewMisAnuncios = view.findViewById(R.id.recycler_misAnuncios);
        recyclerViewMisAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));
        bbdd = FirebaseFirestore.getInstance();


        Query query = bbdd.collection("anuncios").whereEqualTo("uid", uid);


        FirestoreRecyclerOptions<Anuncio> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Anuncio>()
                .setQuery(query, Anuncio.class).build();

        //mAdapter = new MisAnunciosAdapter(firestoreRecyclerOptions);
        mAdapter = new MisAnunciosAdapter(firestoreRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull Anuncio anuncio) {

                holder.getTitulo().setText(anuncio.getTitulo());
                holder.getTiempo().setText(anuncio.getTiempo());
                holder.getDescripcion().setText(anuncio.getDescripcion());
                holder.getFechaPublicacion().setText("Publicado: " + anuncio.getFechaPublicacion());

                //Ponemos datos de la familia
                String imagen = anuncio.getImg();

                if (imagen.isEmpty()) {
                    Picasso.get().load(R.drawable.fotoperfil).into(holder.getImg());
                } else {
                    Picasso.get().load(imagen).into(holder.getImg());
                }


                holder.getDireccion().setText(anuncio.getDireccion());
                holder.getNombre().setText(anuncio.getNombre());


                final String idAnuncio = anuncio.getIdAnuncio();

                // Obtenemos el cardview de itemCanguro que hemos instanciado en el onBindViewHolder de AdapterCangruo
                holder.getBtnBorrarAnuncio().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        holder.getBtnBorrarAnuncio().playAnimation();

                        MaterialAlertDialogBuilder builder =new MaterialAlertDialogBuilder(getContext(), R.style.MyMaterialAlertDialog);
                        builder.setTitle("Eliminar mi anuncio");
                        builder.setMessage("¿Estás seguro/a de que quieres eliminar tu anuncio?");
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //ELIMINAMOS ANUNCIO
                                bbdd.collection("anuncios")
                                        .document(idAnuncio)
                                        .delete();
                            }
                        });
                        builder.show();

                    }
                });

            }
        };

        mAdapter.notifyDataSetChanged();
        recyclerViewMisAnuncios.setAdapter(mAdapter);

        return view;
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
