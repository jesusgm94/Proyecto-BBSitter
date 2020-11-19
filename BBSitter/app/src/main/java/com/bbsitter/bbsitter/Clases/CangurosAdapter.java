package com.bbsitter.bbsitter.Clases;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.R;

import de.hdodenhof.circleimageview.CircleImageView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class CangurosAdapter extends FirestoreRecyclerAdapter<Canguro, CangurosAdapter.ViewHolder>{


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CangurosAdapter(@NonNull FirestoreRecyclerOptions<Canguro> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Canguro canguro) {

        String dist = "0";
        Random randomStars = new Random();

        holder.nombre.setText(canguro.getNombre());
        holder.precioHora.setText(String.valueOf(canguro.getPrecioHora()) + " €");
        holder.edad.setText("31 años");
        holder.nombre.setText(canguro.getNombre());

        holder.ratingBar.setRating(randomStars.nextInt(6)+1);


        // Poner FOTO
        String urlFoto = canguro.getImg().toString();
        Picasso.get().load(urlFoto).into(holder.imagen);

        // Distancia puesta a mano. Deberiamos calcular la edad en la clase Canguro y obtenerla de ahi
        holder.distancia.setText(dist + " kms");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_canguro, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imagen;
        TextView nombre, edad, distancia, precioHora;
        RatingBar ratingBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.itemImagenCanguro);
            nombre = itemView.findViewById(R.id.itemNombreCanguro);
            edad = itemView.findViewById(R.id.itemEdadCanguro);
            distancia = itemView.findViewById(R.id.itemDistanciaCanguro);
            precioHora = itemView.findViewById(R.id.itemPrecioHoraCanguro);
            ratingBar = itemView.findViewById(R.id.itemRatingBarCanguro);
        }
    }
}
