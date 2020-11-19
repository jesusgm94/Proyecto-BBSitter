package com.bbsitter.bbsitter.Clases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CanguroAdapter extends RecyclerView.Adapter<CanguroAdapter.CanguroViewHolder>{

    private List<Canguro> listaCanguros;

    public CanguroAdapter(List<Canguro> listaCanguros) {
        this.listaCanguros = listaCanguros;
    }

    @NonNull
    @Override
    public CanguroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_canguro, null, false);
       return new CanguroViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CanguroViewHolder holder, int position) {
        holder.bindCanguro(listaCanguros.get(position));
    }

    @Override
    public int getItemCount() {
        return listaCanguros.size();
    }


    class CanguroViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imagen;
        TextView nombre, edad, distancia, precioHora;
        RatingBar ratingBar;


        public CanguroViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.itemImagenCanguro);
            nombre = itemView.findViewById(R.id.itemNombreCanguro);
            edad = itemView.findViewById(R.id.itemEdadCanguro);
            distancia = itemView.findViewById(R.id.itemDistanciaCanguro);
            precioHora = itemView.findViewById(R.id.itemPrecioHoraCanguro);
            ratingBar = itemView.findViewById(R.id.itemRatingBarCanguro);

        }

        void bindCanguro (final Canguro canguro){

            String dist = "0";
            Random randomStars = new Random();

            // imagen.setImageURI(canguro.getUrlFoto().toString());

            nombre.setText(canguro.getNombre());
            edad.setText(String.valueOf(canguro.getEdad()));
            precioHora.setText(String.valueOf(canguro.getPrecioHora()) + " €");

            ratingBar.setRating(randomStars.nextInt(6)+1);

            // Calcular la distancia
            distancia.setText(dist + " kms");

        }
    }
}
