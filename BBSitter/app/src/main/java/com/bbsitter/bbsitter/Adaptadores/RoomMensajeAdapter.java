package com.bbsitter.bbsitter.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Clases.RoomChat;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomMensajeAdapter extends FirestoreRecyclerAdapter<RoomChat, RoomMensajeAdapter.ViewHolder> {

    public static final int TIPO_MENSAJE_DERECHA = 1;
    public static final int TIPO_MENSAJE_IZQUIERDA = 0;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String emisor = mAuth.getCurrentUser().getUid();

    public RoomMensajeAdapter(@NonNull FirestoreRecyclerOptions<RoomChat> options) {
        super(options);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).getEmisor().equals(emisor))
            return TIPO_MENSAJE_DERECHA;
        else
            return TIPO_MENSAJE_IZQUIERDA;
    }

    @NonNull
    @Override
    public RoomMensajeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_derecha, viewGroup, false);

        // Depende de quien sea el mensaje irá a un sitio u a otro
        View view;
        if(i == TIPO_MENSAJE_DERECHA) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_derecha, viewGroup, false);
        }
        else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_izquierda, viewGroup, false);
        }
        return new  ViewHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull RoomChat roomChat) {

        holder.mensaje.setText(roomChat.getMensaje());
        holder.horaMensaje.setText(roomChat.getFecha());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView mensaje, horaMensaje;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            mensaje = itemView.findViewById(R.id.mensaje_derecha);
            horaMensaje = itemView.findViewById(R.id.hora_mensaje_derecha);

        }


        public TextView getMensaje() {
            return mensaje;
        }

        public void setMensaje(TextView mensaje) {
            this.mensaje = mensaje;
        }

        public TextView getHoraMensaje() {
            return horaMensaje;
        }

        public void setHoraMensaje(TextView horaMensaje) {
            this.horaMensaje = horaMensaje;
        }


    }


}
