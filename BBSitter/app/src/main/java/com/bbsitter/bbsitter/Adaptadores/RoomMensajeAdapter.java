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

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomMensajeAdapter extends FirestoreRecyclerAdapter<RoomChat, RoomMensajeAdapter.ViewHolder> {

    public static final int TIPO_MENSAJE_DERECHA = 1;
    public static final int TIPO_MENSAJE_IZQUIERDA = 0;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RoomMensajeAdapter(@NonNull FirestoreRecyclerOptions<RoomChat> options) {
        super(options);
    }


    @NonNull
    @Override
    public RoomMensajeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_canguro, viewGroup, false);
        return new  ViewHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull RoomChat model) {



    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Card View para poder pinchar sobre el item del Recycler
        CardView cardViewCanguro;

        CircleImageView img;
        TextView nombre, edad, distancia, precioHora;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.itemImagenCanguro);
            nombre = itemView.findViewById(R.id.itemNombreCanguro);
            edad = itemView.findViewById(R.id.itemEdadCanguro);
            distancia = itemView.findViewById(R.id.itemDistanciaCanguro);
            precioHora = itemView.findViewById(R.id.itemPrecioHoraCanguro);
            ratingBar = itemView.findViewById(R.id.itemRatingBarCanguro);

            cardViewCanguro = itemView.findViewById(R.id.cardViewCanguro);
        }

        public CircleImageView getImg() {
            return img;
        }

        public void setImg(CircleImageView img) {
            this.img = img;
        }

        public TextView getNombre() {
            return nombre;
        }

        public void setNombre(TextView nombre) {
            this.nombre = nombre;
        }

        public TextView getEdad() {
            return edad;
        }

        public void setEdad(TextView edad) {
            this.edad = edad;
        }

        public TextView getDistancia() {
            return distancia;
        }

        public void setDistancia(TextView distancia) {
            this.distancia = distancia;
        }

        public TextView getPrecioHora() {
            return precioHora;
        }

        public void setPrecioHora(TextView precioHora) {
            this.precioHora = precioHora;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public void setRatingBar(RatingBar ratingBar) {
            this.ratingBar = ratingBar;
        }

        public CardView getCardViewCanguro() {
            return cardViewCanguro;
        }

        public void setCardViewCanguro(CardView cardViewCanguro) {
            this.cardViewCanguro = cardViewCanguro;
        }

    }

}
