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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaCangurosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaCangurosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewListaCanguros;
    private CanguroAdapter mAdapter;

    String dist = "0";
    Random randomStars = new Random();



    public ListaCangurosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaCangurosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaCangurosFragment newInstance(String param1, String param2) {
        ListaCangurosFragment fragment = new ListaCangurosFragment();
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
        View view = inflater.inflate(R.layout.fragment_lista_canguros, container, false);


        recyclerViewListaCanguros = view.findViewById(R.id.recycler_ListaCanguros);
        recyclerViewListaCanguros.setLayoutManager(new LinearLayoutManager(getContext()));
        bbdd = FirebaseFirestore.getInstance();

        Query query = bbdd.collection("canguros");

        FirestoreRecyclerOptions<Canguro> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Canguro>()
                .setQuery(query, Canguro.class).build();

        //mAdapter = new CanguroAdapter(firestoreRecyclerOptions);
        mAdapter = new CanguroAdapter(firestoreRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Canguro canguro) {

                // Poner FOTO
                String img = canguro.getImg();
                Picasso.get().load(img).into(holder.getImg());

                holder.getNombre().setText(canguro.getNombre());
                holder.getEdad().setText(String.valueOf(canguro.getEdad()) + " años");
                holder.getPrecioHora().setText(canguro.getPrecioHora() + " €");

                holder.getRatingBar().setRating(randomStars.nextInt(6)+1);
                // Calcular la distancia
                holder.getDistancia().setText(dist + " kms");

                String uid = canguro.getUid().toString();

                //Canguro canguro1 = new Canguro(getSnapshots().getSnapshot(position).getId(),canguro);

                // Obtenemos el cardview de itemCanguro que hemos instanciado en el onBindViewHolder de AdapterCangruo
                holder.getCardViewCanguro().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PerfilCanguroFragment perfilCanguroFragment = new PerfilCanguroFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, perfilCanguroFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                });

            }
        };

        mAdapter.notifyDataSetChanged();
        recyclerViewListaCanguros.setAdapter(mAdapter);


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