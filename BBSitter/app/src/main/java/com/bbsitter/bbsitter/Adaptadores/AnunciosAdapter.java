package com.bbsitter.bbsitter.Adaptadores;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Clases.Anuncio;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnunciosAdapter extends FirestoreRecyclerAdapter<Anuncio, AnunciosAdapter.ViewHolder>
                            implements View.OnClickListener{


    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    //Para escuchar los click
    private View.OnClickListener listener;
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
        holder.fechaPublicacion.setText("Publicado: " + anuncio.getFechaPublicacion());

        //Ponemos datos de la familia
        String img = anuncio.getImg();
        Picasso.get().load(img).into(holder.img);
        holder.direccion.setText(anuncio.getDireccion());
        holder.nombre.setText(anuncio.getNombre());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_anuncios, viewGroup, false);

        view.setOnClickListener(this);

        return new AnunciosAdapter.ViewHolder(view);
    }


    public void setOnClickListener (View.OnClickListener listener)
    {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        if(listener != null)
        {
            listener.onClick(view);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, descripcion, tiempo, fechaPublicacion, direccion, nombre;
        CircleImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.itemTituloAnuncio);
            descripcion = itemView.findViewById(R.id.iditemDescripcionAnuncio);
            tiempo = itemView.findViewById(R.id.itemTiempoAnuncio);
            fechaPublicacion = itemView.findViewById(R.id.itemFechaPublicacion);

            img = itemView.findViewById(R.id.itemImagenPerfilAnuncio);
            direccion = itemView.findViewById(R.id.itemCiudadAnuncio);
            nombre = itemView.findViewById(R.id.itemNombreAnuncio);
        }


    }

}
