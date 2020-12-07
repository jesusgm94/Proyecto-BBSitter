package com.bbsitter.bbsitter.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Clases.RoomChat;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends FirestoreRecyclerAdapter<RoomChat, ChatsAdapter.ViewHolder> {


    public ChatsAdapter(@NonNull FirestoreRecyclerOptions<RoomChat> options) {
        super(options);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_derecha, viewGroup, false);

        // Depende de quien sea el mensaje irá a un sitio u a otro
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat, viewGroup, false);
        return new ChatsAdapter.ViewHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position, @NonNull RoomChat roomChat) {

        holder.getNombreReceptor().setText(roomChat.getReceptor());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_itemChats;

        CircleImageView imgReceptor;
        TextView nombreReceptor, horaMensaje, mensaje;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreReceptor = itemView.findViewById(R.id.nombreReceptorChat);
            horaMensaje = itemView.findViewById(R.id.fechaUltimoMensaje);
            mensaje = itemView.findViewById(R.id.ultimoMensajeChat);
            imgReceptor = itemView.findViewById(R.id.imageReceptor);

            layout_itemChats = itemView.findViewById(R.id.layout_itemChats);

        }

        public CircleImageView getImgReceptor() {
            return imgReceptor;
        }

        public void setImgReceptor(CircleImageView imgReceptor) {
            this.imgReceptor = imgReceptor;
        }

        public TextView getNombreReceptor() {
            return nombreReceptor;
        }

        public void setNombreReceptor(TextView nombreReceptor) {
            this.nombreReceptor = nombreReceptor;
        }

        public TextView getHoraMensaje() {
            return horaMensaje;
        }

        public void setHoraMensaje(TextView horaMensaje) {
            this.horaMensaje = horaMensaje;
        }

        public TextView getMensaje() {
            return mensaje;
        }

        public void setMensaje(TextView mensaje) {
            this.mensaje = mensaje;
        }
    }


}