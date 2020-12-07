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
import com.bbsitter.bbsitter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatsCanguroFragment extends Fragment {

    private ChatsCanguroViewModel mViewModel;
    private FirebaseFirestore bbdd;
    private FirebaseAuth mAuth;

    RecyclerView recyclerViewListaChats;
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

        recyclerViewListaChats = view.findViewById(R.id.recycler_chats);
        recyclerViewListaChats.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatsCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

}