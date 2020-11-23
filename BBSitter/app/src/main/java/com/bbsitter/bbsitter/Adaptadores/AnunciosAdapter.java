package com.bbsitter.bbsitter.Adaptadores;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Clases.Anuncio;
import com.bbsitter.bbsitter.Clases.Canguro;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;


import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnunciosAdapter extends FirestoreRecyclerAdapter<Anuncio, AnunciosAdapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AnunciosAdapter(@NonNull FirestoreRecyclerOptions<Anuncio> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Anuncio anuncio) {

        holder.titulo.setText(anuncio.getTitulo());
        holder.tiempo.setText(anuncio.getTiempo());
        holder.descripcion.setText(anuncio.getDescripcion());
        holder.fechaPublicacion.setText(anuncio.getFechaPublicacion());


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_anuncios, viewGroup, false);
        return new AnunciosAdapter.ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, descripcion, tiempo, fechaPublicacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.itemTituloAnuncio);
            descripcion = itemView.findViewById(R.id.iditemDescripcionAnuncio);
            tiempo = itemView.findViewById(R.id.itemTiempoAnuncio);
            fechaPublicacion = itemView.findViewById(R.id.itemFechaPublicacion);
        }


    }

}
