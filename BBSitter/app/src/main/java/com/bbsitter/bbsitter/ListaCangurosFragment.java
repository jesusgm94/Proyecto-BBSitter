package com.bbsitter.bbsitter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbsitter.bbsitter.Clases.Canguro;
import com.bbsitter.bbsitter.Clases.CanguroAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
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

    RecyclerView recyclerViewListaCanguros;

    List<Canguro> listaCanguros;


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

        bbdd = FirebaseFirestore.getInstance();


        listaCanguros = new ArrayList<>();
        recyclerViewListaCanguros = view.findViewById(R.id.recycler_ListaCanguros);
        recyclerViewListaCanguros.setLayoutManager(new LinearLayoutManager(getContext()));

        rellenarListaCanguros();

        CanguroAdapter adapterCanguro = new CanguroAdapter(listaCanguros);
        recyclerViewListaCanguros.setAdapter(adapterCanguro);

        return view;
    }

    private void rellenarListaCanguros() {

        Canguro canguro = new Canguro();
        canguro.setNombre("David");
        canguro.setEdad(31);
        canguro.setPrecioHora(10.50);

        listaCanguros.add(canguro);

    }
}