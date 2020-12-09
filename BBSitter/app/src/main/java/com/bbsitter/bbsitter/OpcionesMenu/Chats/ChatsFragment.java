package com.bbsitter.bbsitter.OpcionesMenu.Chats;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.CanguroAdapter;
import com.bbsitter.bbsitter.Adaptadores.ChatsAdapter;
import com.bbsitter.bbsitter.Adaptadores.RoomMensajeAdapter;
import com.bbsitter.bbsitter.Clases.Canguro;
import com.bbsitter.bbsitter.Clases.Chat;
import com.bbsitter.bbsitter.Clases.RoomChat;
import com.bbsitter.bbsitter.OpcionesMenuCanguro.Perfil.PerfilCanguroFragment;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ChatsFragment extends Fragment {

    private ChatsViewModel mensajesViewModel;

    private FirebaseFirestore bbdd;
    private FirebaseAuth mAuth;

    RecyclerView recyclerViewListaChats;
    ChatsAdapter chatsAdapter;


    private String emisor = "";
    private String receptor = "";
    private String usuario = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mensajesViewModel =  ViewModelProviders.of(this).get(ChatsViewModel.class);

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        bbdd = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        usuario = mAuth.getCurrentUser().getUid();

        recyclerViewListaChats = view.findViewById(R.id.recycler_chats);
        recyclerViewListaChats.setLayoutManager(new LinearLayoutManager(getContext()));


        // CONSULTA para usar en FirestoreRecyclerOptions
        Query query = bbdd.collection("chat").whereEqualTo("emisor", usuario);

        FirestoreRecyclerOptions<Chat> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class).build();

        chatsAdapter = new ChatsAdapter(firestoreRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position, @NonNull Chat chat) {

                holder.getNombreReceptor().setText(chat.getId());
            }

        };

        chatsAdapter.notifyDataSetChanged();
        recyclerViewListaChats.setAdapter(chatsAdapter);

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
