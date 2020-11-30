package com.bbsitter.bbsitter.OpcionesMenu.Anuncios;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bbsitter.bbsitter.Login.ProgressBarRegistro;
import com.bbsitter.bbsitter.Login.RegistroUsuarioActivity;
import com.bbsitter.bbsitter.ProgressBarCargando;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetalleAnuncioFragment extends Fragment {

    private DetalleAnuncioViewModel mViewModel;

    private CircleImageView imagenPerfilDetalleAnuncio;
    private TextView tvDescripcionDetalleAnuncio,tvTituloDetalleAnuncio;

    private String idAnuncio;

    private FirebaseFirestore bbdd;




    public static DetalleAnuncioFragment newInstance() {
        return new DetalleAnuncioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_anuncio_fragment, container, false);
        final ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());


        progressBarCargando.StarProgressBar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarCargando.finishProgressBar();


            }
        }, 2000);

        bbdd = FirebaseFirestore.getInstance();
        //Recogemos el uid de la familia de InicioCanguroFragment
        Bundle data = this.getArguments();
        if(data != null){
            idAnuncio = data.getString("idAnuncio");
        }

        imagenPerfilDetalleAnuncio = view.findViewById(R.id.itemImagenPerfilAnuncio);
        tvTituloDetalleAnuncio = view.findViewById(R.id.tvTituloDetalleAnuncio);
        tvDescripcionDetalleAnuncio = view.findViewById(R.id.tvDescripcionDetalleAnuncio);

        cargarDatosAnuncio();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleAnuncioViewModel.class);
    }

    private void cargarDatosAnuncio()
    {

        bbdd.collection("anuncios")
                .whereEqualTo("idAnuncio", idAnuncio)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Recogemos los datos de la base de datos
                                String tituloAnuncio =  document.get("titulo").toString();
                                String descripcionAnuncio = document.get("descripcion").toString();
                                //String imagenFamiliaAnuncio = document.get("img").toString();



                                //Agrega una nueva imagen desde una url usando Picasso.
                                //Picasso.get().load(imagenFamiliaAnuncio).into(imagenPerfilDetalleAnuncio);

                                //Agrega nuevo nombre
                                tvTituloDetalleAnuncio.setText(tituloAnuncio);
                                tvDescripcionDetalleAnuncio.setText(descripcionAnuncio);



                            }
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}