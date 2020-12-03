package com.bbsitter.bbsitter.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bbsitter.bbsitter.Clases.Hijos;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HijosAdapter extends FirestoreRecyclerAdapter<Hijos, HijosAdapter.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HijosAdapter(@NonNull FirestoreRecyclerOptions<Hijos> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HijosAdapter.ViewHolder holder, int position, @NonNull Hijos hijos) {

        holder.nombre.setText(hijos.getNombre());
        holder.edad.setText(hijos.getEdad() + " años");
        holder.otrosDatos.setText(hijos.getOtrosDatos());

    }

    @NonNull
    @Override
    public HijosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hijos, viewGroup, false);

        return new HijosAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, edad, otrosDatos;
        LottieAnimationView btnBorrarHijo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.itemNombreHijo);
            edad = itemView.findViewById(R.id.itemEdadHijo);
            otrosDatos = itemView.findViewById(R.id.itemDatosInteresHijo);
            btnBorrarHijo = itemView.findViewById(R.id.lottieBorrarHijo);
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

        public TextView getOtrosDatos() {
            return otrosDatos;
        }

        public void setOtrosDatos(TextView otrosDatos) {
            this.otrosDatos = otrosDatos;
        }

        public LottieAnimationView getBtnBorrarHijo() {
            return btnBorrarHijo;
        }

        public void setBtnBorrarHijo(LottieAnimationView btnBorrarHijo) {
            this.btnBorrarHijo = btnBorrarHijo;
        }
    }

}
