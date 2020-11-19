package com.bbsitter.bbsitter.Clases;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;


import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CanguroAdapter extends FirestoreRecyclerAdapter<Canguro, CanguroAdapter.ViewHolder> {

    String dist = "0";
    Random randomStars = new Random();

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
        Picasso.get().load(img).into(holder.img);

        holder.nombre.setText(canguro.getNombre());
        holder.edad.setText(String.valueOf(canguro.getEdad()));
        holder.precioHora.setText(canguro.getPrecioHora() + " €");

        holder.ratingBar.setRating(randomStars.nextInt(6)+1);
        // Calcular la distancia
        holder.distancia.setText(dist + " kms");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_canguro, viewGroup, false);
        return new ViewHolder(view);
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

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
        }


    }

}


