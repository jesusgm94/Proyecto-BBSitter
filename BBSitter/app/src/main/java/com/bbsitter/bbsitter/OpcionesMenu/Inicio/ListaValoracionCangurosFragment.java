package com.bbsitter.bbsitter.OpcionesMenu.Inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.CanguroAdapter;
import com.bbsitter.bbsitter.Clases.Canguro;
import com.bbsitter.bbsitter.OpcionesMenuCanguro.PerfilCanguroFragment;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaValoracionCangurosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaValoracionCangurosFragment extends Fragment {

    LatLng MIUBICACION;

    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewListaValoracionCanguros;
    private CanguroAdapter mAdapter;

    String dist = "0";
    Random distancia = new Random();


    public ListaValoracionCangurosFragment() {
        // Required empty public constructor
    }


    public static ListaValoracionCangurosFragment newInstance(String param1, String param2) {
        ListaValoracionCangurosFragment fragment = new ListaValoracionCangurosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_valoracion_canguros, container, false);

        // RECYCLER VIEW ****************
        recyclerViewListaValoracionCanguros = view.findViewById(R.id.recycler_ListaValoracionCanguros);
        recyclerViewListaValoracionCanguros.setLayoutManager(new LinearLayoutManager(getContext()));
        bbdd = FirebaseFirestore.getInstance();


        Query query = bbdd.collection("canguros").orderBy("rating", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Canguro> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Canguro>()
                .setQuery(query, Canguro.class).build();

        mAdapter = new CanguroAdapter(firestoreRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Canguro canguro) {

                // Poner FOTO
                String img = canguro.getImg();
                Picasso.get().load(img).into(holder.getImg());

                holder.getNombre().setText(canguro.getNombre());
                holder.getEdad().setText(String.valueOf(canguro.getEdad()) + " años");
                holder.getPrecioHora().setText(canguro.getPrecioHora() + " €");

                holder.getRatingBar().setRating(canguro.getRating());
                // Calcular la distancia
                holder.getDistancia().setText(dist + " kms");

                final String uid = canguro.getUid();


                // Obtenemos el cardview de itemCanguro que hemos instanciado en el onBindViewHolder de AdapterCangruo
                holder.getCardViewCanguro().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Llevamos el uid con un Bundle a PerfilCanguroFragment
                        PerfilCanguroFragment perfilCanguroFragment = new PerfilCanguroFragment();
                        Bundle data = new Bundle();
                        data.putString("uid", uid);
                        perfilCanguroFragment.setArguments(data);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, perfilCanguroFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                });

            }
        };

        mAdapter.notifyDataSetChanged();
        recyclerViewListaValoracionCanguros.setAdapter(mAdapter);



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