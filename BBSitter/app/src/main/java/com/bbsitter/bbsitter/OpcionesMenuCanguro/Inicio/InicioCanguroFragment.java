package com.bbsitter.bbsitter.OpcionesMenuCanguro.Inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.AnunciosAdapter;
import com.bbsitter.bbsitter.Clases.Anuncio;
import com.bbsitter.bbsitter.OpcionesMenu.Anuncios.DetalleAnuncioFragment;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioCanguroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioCanguroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewListaAnuncios;
    private AnunciosAdapter mAdapter;


    public InicioCanguroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioCanguroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioCanguroFragment newInstance(String param1, String param2) {
        InicioCanguroFragment fragment = new InicioCanguroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.inicio_canguro_fragment, container, false);


        recyclerViewListaAnuncios = view.findViewById(R.id.recycler_ListaAnuncios);
        recyclerViewListaAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));

        bbdd = FirebaseFirestore.getInstance();

        //POR DEFECTO SALE ESTA PANTALLA
        Query query = bbdd.collection("anuncios")
                .orderBy("fechaPublicacion", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Anuncio> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Anuncio>()
                .setQuery(query, Anuncio.class).build();
        //mAdapter = new AnunciosAdapter(firestoreRecyclerOptions);

        mAdapter = new AnunciosAdapter(firestoreRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Anuncio anuncio) {

                // Poner FOTO
                String img = anuncio.getImg();
                Picasso.get().load(img).into(holder.getImg());

                holder.getTitulo().setText(anuncio.getTitulo());
                holder.getDireccion().setText(anuncio.getDireccion());
                holder.getTiempo().setText(anuncio.getTiempo());
                holder.getDescripcion().setText(anuncio.getDescripcion());
                holder.getFechaPublicacion().setText(anuncio.getFechaPublicacion());
                holder.getNombre().setText(anuncio.getNombre());


                //Cogemos el id del anuncio
                final String idAnuncio = mAdapter.getSnapshots().getSnapshot(position).getId();

                // Obtenemos el cardview de itemCanguro que hemos instanciado en el onBindViewHolder de AdapterCangruo
                holder.getCardViewAnuncio().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Llevamos el idAnuncio a DetalleAnunciosFragment
                        DetalleAnuncioFragment detalleAnuncioFragment = new DetalleAnuncioFragment();
                        Bundle data = new Bundle();
                        data.putString("idAnuncio", idAnuncio);
                        detalleAnuncioFragment.setArguments(data);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_canguro, detalleAnuncioFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                });

            }
        };

        mAdapter.notifyDataSetChanged();
        recyclerViewListaAnuncios.setAdapter(mAdapter);


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