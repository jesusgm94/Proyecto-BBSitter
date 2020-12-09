package com.bbsitter.bbsitter.OpcionesMenuCanguro.Chats;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbsitter.bbsitter.Adaptadores.ChatsAdapter;
import com.bbsitter.bbsitter.Clases.Chat;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.dmoral.toasty.Toasty;

public class ChatsCanguroFragment extends Fragment {

    private ChatsCanguroViewModel mViewModel;
    private FirebaseFirestore bbdd;
    private FirebaseAuth mAuth;

    RecyclerView recyclerViewListaChatsCanguro;
    ChatsAdapter chatsAdapter;


    private String emisor = "";
    private String receptor = "";
    private String usuario = "";

    public static ChatsCanguroFragment newInstance() {
        return new ChatsCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.chats_canguro_fragment, container, false);

        bbdd = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        usuario = mAuth.getCurrentUser().getUid();

        recyclerViewListaChatsCanguro = view.findViewById(R.id.recycler_chatsCanguros);
        recyclerViewListaChatsCanguro.setLayoutManager(new LinearLayoutManager(getContext()));

        // CONSULTA para usar en FirestoreRecyclerOptions
        Query query = bbdd.collection("chat").whereEqualTo("receptor", usuario);

        FirestoreRecyclerOptions<Chat> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class).build();

        chatsAdapter = new ChatsAdapter(firestoreRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position, @NonNull Chat chat) {

                holder.getNombreReceptor().setText(chat.getId());
            }

        };

        chatsAdapter.notifyDataSetChanged();
        recyclerViewListaChatsCanguro.setAdapter(chatsAdapter);

        return view;

    }



    @Override
    public void onStart() {
        super.onStart();
        chatsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        chatsAdapter.stopListening();
    }

}