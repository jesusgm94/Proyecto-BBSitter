package com.bbsitter.bbsitter.Adaptadores;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Clases.Canguro;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CanguroAdapter extends FirestoreRecyclerAdapter<Canguro, CanguroAdapter.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CanguroAdapter(@NonNull FirestoreRecyclerOptions<Canguro> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Canguro canguro) {

        // Poner FOTO
        String img = canguro.getImg();

        if (img.isEmpty()) {
            Picasso.get().load(R.drawable.fotoperfil).into(holder.img);
        } else{
            Picasso.get().load(img).into(holder.img);
        }


        holder.nombre.setText(canguro.getNombre());
        holder.edad.setText((canguro.getEdad()) + " años" );
        holder.precioHora.setText(canguro.getPrecioHora() + " €");
        holder.ratingBar.setRating(canguro.getRating());


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_canguro, viewGroup, false);
        return new ViewHolder(view);

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