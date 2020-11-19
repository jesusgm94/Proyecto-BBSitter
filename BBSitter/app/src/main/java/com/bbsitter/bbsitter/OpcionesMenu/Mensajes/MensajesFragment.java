package com.bbsitter.bbsitter.OpcionesMenu.Mensajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Clases.Canguro;
import com.bbsitter.bbsitter.Clases.CangurosAdapter;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MensajesFragment extends Fragment {

    private MensajesViewModel mensajesViewModel;

    private FirebaseFirestore bbdd;

    RecyclerView recyclerViewListaCanguros;
    CangurosAdapter adapterCanguro;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mensajesViewModel =  ViewModelProviders.of(this).get(MensajesViewModel.class);

        View view = inflater.inflate(R.layout.fragment_mensajes, container, false);

        bbdd = FirebaseFirestore.getInstance();

        recyclerViewListaCanguros = view.findViewById(R.id.recycler_chats);
        recyclerViewListaCanguros.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = bbdd.collection("canguros");

        FirestoreRecyclerOptions<Canguro> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Canguro>()
                .setQuery(query, Canguro.class).build();

        adapterCanguro = new CangurosAdapter(firestoreRecyclerOptions);
        adapterCanguro.notifyDataSetChanged();

        recyclerViewListaCanguros.setAdapter(adapterCanguro);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterCanguro.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterCanguro.stopListening();
    }
}
