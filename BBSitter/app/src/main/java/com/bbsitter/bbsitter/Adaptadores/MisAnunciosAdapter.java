package com.bbsitter.bbsitter.Adaptadores;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bbsitter.bbsitter.Clases.Anuncio;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MisAnunciosAdapter extends FirestoreRecyclerAdapter<Anuncio, MisAnunciosAdapter.ViewHolder>
        /*implements View.OnClickListener*/{


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
    public MisAnunciosAdapter(@NonNull FirestoreRecyclerOptions<Anuncio> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Anuncio anuncio) {

        holder.titulo.setText(anuncio.getTitulo());
        holder.tiempo.setText(anuncio.getTiempo());
        holder.descripcion.setText(anuncio.getDescripcion());
        holder.fechaPublicacion.setText("Publicado: " + anuncio.getFechaPublicacion());



        //Ponemos datos de la familia
        String imagen = anuncio.getImg();

        if (imagen.isEmpty()) {
            Picasso.get().load(R.drawable.fotoperfil).into(holder.img);
        } else{
            Picasso.get().load(imagen).into(holder.img);
        }


        holder.direccion.setText(anuncio.getDireccion());
        holder.nombre.setText(anuncio.getNombre());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_misanuncios, viewGroup, false);

        //view.setOnClickListener(this);

        return new MisAnunciosAdapter.ViewHolder(view);
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewAnuncio;

        TextView titulo, descripcion, tiempo, fechaPublicacion, direccion, nombre;
        CircleImageView img;
        LottieAnimationView btnBorrarAnuncio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.itemTituloAnuncio);
            descripcion = itemView.findViewById(R.id.iditemDescripcionAnuncio);
            tiempo = itemView.findViewById(R.id.itemTiempoAnuncio);
            fechaPublicacion = itemView.findViewById(R.id.itemFechaPublicacion);

            img = itemView.findViewById(R.id.itemImagenPerfilAnuncio);
            direccion = itemView.findViewById(R.id.itemCiudadAnuncio);
            nombre = itemView.findViewById(R.id.itemNombreAnuncio);

            btnBorrarAnuncio = itemView.findViewById(R.id.lottieBorrarAnuncio);

            cardViewAnuncio = itemView.findViewById(R.id.cardViewAnuncio);

        }

        public TextView getTitulo() {
            return titulo;
        }

        public void setTitulo(TextView titulo) {
            this.titulo = titulo;
        }

        public TextView getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(TextView descripcion) {
            this.descripcion = descripcion;
        }

        public TextView getTiempo() {
            return tiempo;
        }

        public void setTiempo(TextView tiempo) {
            this.tiempo = tiempo;
        }

        public TextView getFechaPublicacion() {
            return fechaPublicacion;
        }

        public void setFechaPublicacion(TextView fechaPublicacion) {
            this.fechaPublicacion = fechaPublicacion;
        }

        public TextView getDireccion() {
            return direccion;
        }

        public void setDireccion(TextView direccion) {
            this.direccion = direccion;
        }

        public TextView getNombre() {
            return nombre;
        }

        public void setNombre(TextView nombre) {
            this.nombre = nombre;
        }

        public CircleImageView getImg() {
            return img;
        }

        public void setImg(CircleImageView img) {
            this.img = img;
        }

        public CardView getCardViewAnuncio() {
            return cardViewAnuncio;
        }

        public void setCardViewAnuncio(CardView cardViewAnuncio) {
            this.cardViewAnuncio = cardViewAnuncio;
        }

        public LottieAnimationView getBtnBorrarAnuncio() {
            return btnBorrarAnuncio;
        }

        public void setBtnBorrarAnuncio(LottieAnimationView btnBorrarAnuncio) {
            this.btnBorrarAnuncio = btnBorrarAnuncio;
        }
    }

}

