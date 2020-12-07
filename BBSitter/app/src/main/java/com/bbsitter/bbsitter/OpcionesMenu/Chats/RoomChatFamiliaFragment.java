package com.bbsitter.bbsitter.OpcionesMenu.Chats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.RoomMensajeAdapter;
import com.bbsitter.bbsitter.Clases.RoomChat;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomChatFamiliaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomChatFamiliaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseFirestore bbdd;

    private String emisor = "";
    private String receptor = "";

    ImageButton btnEnviar;
    EditText etMensaje;

    private FirebaseAuth mAuth;
    private RecyclerView recyclerViewListaMensajes;
    private RoomMensajeAdapter mAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoomChatFamiliaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomChatFamiliaFragment newInstance(String param1, String param2) {
        RoomChatFamiliaFragment fragment = new RoomChatFamiliaFragment();
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
        View view = inflater.inflate(R.layout.fragment_room_chat_familia, container, false);

        bbdd = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnEnviar = view.findViewById(R.id.btnEnviar);
        etMensaje = view.findViewById(R.id.etMensaje);

        emisor = mAuth.getCurrentUser().getUid();

        //Recogemos el uid del canguro, que será el receptor del mensaje
        Bundle data = this.getArguments();
        if(data != null){
            receptor = data.getString("uid");
        }

        // RECYCLER VIEW ****************
        recyclerViewListaMensajes= view.findViewById(R.id.recyclerListaMensajes);
        recyclerViewListaMensajes.setLayoutManager(new LinearLayoutManager(getContext()));


        Query query = bbdd.collection("roomChat").document(receptor)
                .collection("mensajes").orderBy("fecha", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<RoomChat> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<RoomChat>()
                .setQuery(query, RoomChat.class).build();

        //mAdapter = new CanguroAdapter(firestoreRecyclerOptions);
        mAdapter = new RoomMensajeAdapter(firestoreRecyclerOptions) {

            public static final int TIPO_MENSAJE_DERECHA = 1;
            public static final int TIPO_MENSAJE_IZQUIERDA = 0;



            @Override
            public int getItemViewType(int position) {
                if(getItem(position).getEmisor().equals(emisor))
                    return TIPO_MENSAJE_DERECHA;
                else
                    return TIPO_MENSAJE_IZQUIERDA;
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull RoomChat roomChat) {
                holder.getMensaje().setText(roomChat.getMensaje());
                holder.getHoraMensaje().setText(roomChat.getFecha().toString());

            }
            @Override
            public RoomMensajeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_derecha, viewGroup, false);
                return new  ViewHolder(view);

            }
        };

        mAdapter.notifyDataSetChanged();

        // Para que nos muestre el ultimo mensaje escrito necesitamos un registerAdapterDataObserver de nuestro adaptador
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerViewListaMensajes.scrollToPosition(mAdapter.getItemCount() - 1);  // El recyclerView hara Scroll hasta el último mensaje
            }
        });
        recyclerViewListaMensajes.setAdapter(mAdapter);



        // BOTON MENSAJE ENVIAR ***
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Si no hay nada escrito no se envia el mensaje para no llenar la base de datos de mensajes vacios
                if (etMensaje.length() == 0)
                    return;

                String mensaje = etMensaje.getText().toString();
                enviarMensaje(mensaje);

                Toast.makeText(getContext(), "Mensaje enviado", Toast.LENGTH_LONG).show();
                etMensaje.setText("");
            }
        });

        return view;
    }

    private void enviarMensaje(String mensaje){


        HashMap<String, Object> mapaMensaje = new HashMap<>();

        // Creamos un objeto Date
        Date fechaPublicacion = new Date();
        // Especificamos un formato
        String DATE_FORMAT = "HH:mm:ss";
        // Create object of SimpleDateFormat and pass the desired date format.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        String fechaHoy = sdf.format(fechaPublicacion);



        mapaMensaje.put("emisor", emisor);
        mapaMensaje.put("receptor", receptor);
        mapaMensaje.put("mensaje", mensaje);
        mapaMensaje.put("fecha", fechaHoy);

        // Guardamos en la base de datos
        bbdd.collection("roomChat").document(receptor).collection("mensajes").add(mapaMensaje);

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